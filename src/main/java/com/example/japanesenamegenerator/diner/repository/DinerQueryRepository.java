package com.example.japanesenamegenerator.diner.repository;

import com.example.japanesenamegenerator.common.util.QueryDslUtil;
import com.example.japanesenamegenerator.diner.application.response.DinerInfoResponseDTO;
import com.example.japanesenamegenerator.diner.domain.DinerInfo;
import com.example.japanesenamegenerator.diner.domain.QDinerInfo;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DinerQueryRepository{

    private final JPAQueryFactory queryFactory;
    private final DataSource dataSource;

    public Page<DinerInfoResponseDTO> findAllByCoordinate(Double lon1, Double lon2, Double lat1, Double lat2, Pageable pageable) {
        QDinerInfo qDinerInfo = QDinerInfo.dinerInfo;

        Sort sort = Sort.by(Sort.Direction.DESC, "reviewCount")
                .and(Sort.by(Sort.Direction.DESC, "ratingAverage"));

        QueryResults<DinerInfo> queryResults = queryFactory
                .select(new QDinerInfo(qDinerInfo))
                .from(qDinerInfo)
                .where(
                        qDinerInfo.lon.between(lon1,lon2),
                        qDinerInfo.lat.between(lat1,lat2)
                )
                .orderBy(QueryDslUtil.getSortedColumn(sort, qDinerInfo))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        List<DinerInfoResponseDTO> list = queryResults.getResults().stream().map(DinerInfoResponseDTO::from).toList();

        return new PageImpl<>(list, pageable, queryResults.getTotal());
    }

    public void upsertDinerInfos(List<DinerInfo> dinerInfos) throws SQLException {
        String sql = "INSERT INTO diner_info (confirm_id, name, address, tel, image, last_cate_name, category_depth1, category_depth2, " +
                "category_depth3, category_depth4, category_depth5, x, y, rating_average, rating_count, review_count, lon, lat, " +
                "appointment, delivery, for_disabled, nursery, package, parking, pet, smoking_room, wifi) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE name = VALUES(name), address = VALUES(address), tel = VALUES(tel), image = VALUES(image), " +
                "last_cate_name = VALUES(last_cate_name), category_depth1 = VALUES(category_depth1), category_depth2 = VALUES(category_depth2), " +
                "category_depth3 = VALUES(category_depth3), category_depth4 = VALUES(category_depth4), category_depth5 = VALUES(category_depth5), " +
                "x = VALUES(x), y = VALUES(y), rating_average = VALUES(rating_average), rating_count = VALUES(rating_count), " +
                "review_count = VALUES(review_count), lon = VALUES(lon), lat = VALUES(lat), appointment = VALUES(appointment), " +
                "delivery = VALUES(delivery), for_disabled = VALUES(for_disabled), nursery = VALUES(nursery), package = VALUES(package), " +
                "parking = VALUES(parking), pet = VALUES(pet), smoking_room = VALUES(smoking_room), wifi = VALUES(wifi);";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (DinerInfo dinerInfo : dinerInfos) {
                ps.setInt(1, dinerInfo.getConfirmId());
                ps.setString(2, dinerInfo.getName());
                ps.setString(3, dinerInfo.getAddress());
                ps.setString(4, dinerInfo.getTel());
                ps.setString(5, dinerInfo.getImage());
                ps.setString(6, dinerInfo.getLastCategoryName());
                ps.setString(7, dinerInfo.getCategoryDepth1());
                ps.setString(8, dinerInfo.getCategoryDepth2());
                ps.setString(9, dinerInfo.getCategoryDepth3());
                ps.setString(10, dinerInfo.getCategoryDepth4());
                ps.setString(11, dinerInfo.getCategoryDepth5());
                ps.setInt(12, dinerInfo.getX());
                ps.setInt(13, dinerInfo.getY());
                ps.setDouble(14, dinerInfo.getRatingAverage());
                ps.setInt(15, dinerInfo.getRatingCount());
                ps.setInt(16, dinerInfo.getReviewCount());
                ps.setDouble(17, dinerInfo.getLon());
                ps.setDouble(18, dinerInfo.getLat());
                ps.setString(19, dinerInfo.getAddinfoAppointment());
                ps.setString(20, dinerInfo.getAddinfoDelivery());
                ps.setString(21, dinerInfo.getAddinfoFordisabled());
                ps.setString(22, dinerInfo.getAddinfoNursery());
                ps.setString(23, dinerInfo.getAddinfoPackage());
                ps.setString(24, dinerInfo.getAddinfoParking());
                ps.setString(25, dinerInfo.getAddinfoPet());
                ps.setString(26, dinerInfo.getAddinfoSmokingroom());
                ps.setString(27, dinerInfo.getAddinfoWifi());

                ps.addBatch();
            }

            ps.executeBatch();  // 배치 처리
            ps.clearBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Upsert failed", e);
        }
    }


}
