package com.example.japanesenamegenerator.diner.repository;

import com.example.japanesenamegenerator.common.util.QueryDslUtil;
import com.example.japanesenamegenerator.diner.application.response.DinerInfoResponseDTO;
import com.example.japanesenamegenerator.diner.domain.*;
import com.example.japanesenamegenerator.diner.entityconverter.StringListConverter;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Repository
@RequiredArgsConstructor
public class DinerQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final DataSource dataSource;
    private final StringListConverter stringListConverter;

    public Page<DinerInfoResponseDTO> findAllByCoordinate(Double lon1, Double lon2, Double lat1, Double lat2, Pageable pageable) {
        QDinerInfo qDinerInfo = QDinerInfo.dinerInfo;

        Sort sort = Sort.by(Sort.Direction.DESC, "reviewCount")
                .and(Sort.by(Sort.Direction.DESC, "ratingAverage"));

        QueryResults<DinerInfo> queryResults = queryFactory
                .select(new QDinerInfo(qDinerInfo))
                .from(qDinerInfo)
                .where(
                        qDinerInfo.lon.between(lon1, lon2),
                        qDinerInfo.lat.between(lat1, lat2)
                )
                .orderBy(QueryDslUtil.getSortedColumn(sort, qDinerInfo))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        List<DinerInfoResponseDTO> list = queryResults.getResults().stream().map(DinerInfoResponseDTO::from).toList();

        return new PageImpl<>(list, pageable, queryResults.getTotal());
    }

    public List<DinerInfo> find10placeByRandom(Double lon1, Double lon2, Double lat1, Double lat2){

        QDinerInfo qDinerInfo = QDinerInfo.dinerInfo;

        return queryFactory
                .select(new QDinerInfo(qDinerInfo))
                .from(qDinerInfo)
                .where(
                        qDinerInfo.lon.between(lon1, lon2),
                        qDinerInfo.lat.between(lat1, lat2)
                )
                .orderBy(Expressions.numberTemplate(Double.class, "function('RAND')").asc())  // 랜덤 정렬
                .limit(10)
                .fetch();
    }



    public void batchUpsertDinerMenus(List<DinerMenu> dinerMenus) {
        String sql = "INSERT INTO diner_menu (confirm_id, description, name, price, recommend) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "description = VALUES(description), " +
                "name = VALUES(name), " +
                "price = VALUES(price), " +
                "recommend = VALUES(recommend)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (DinerMenu dinerMenu : dinerMenus) {
                ps.setLong(1, dinerMenu.getConfirmId());
                ps.setString(2, dinerMenu.getDesc());
                ps.setString(3, dinerMenu.getName());
                ps.setInt(4, dinerMenu.getPrice() == null? 0 : dinerMenu.getPrice() );
                ps.setBoolean(5, dinerMenu.isRecommend());
                ps.addBatch();
            }

            ps.executeBatch(); // 배치 실행
        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 처리 로직 추가
        }
    }

    public void batchUpsertDinerComments(List<DinerComment> dinerComments) {
        String sql = "INSERT INTO diner_comment (confirm_id, username, contents, photo_list, profile, date, thumbnail) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "username = VALUES(username), " +
                "contents = VALUES(contents), " +
                "photo_list = VALUES(photo_list), " +
                "profile = VALUES(profile), " +
                "date = VALUES(date), " +
                "thumbnail = VALUES(thumbnail)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (DinerComment dinerComment : dinerComments) {
                String photos = stringListConverter.convertToDatabaseColumn(dinerComment.getPhotoList());

                ps.setLong(1, dinerComment.getConfirmId());
                ps.setString(2, dinerComment.getUsername());
                ps.setString(3, dinerComment.getContents());
                ps.setString(4, photos);
                ps.setString(5, dinerComment.getProfile());
                ps.setString(6, dinerComment.getDate());
                ps.setString(7, dinerComment.getThumbnail());
                ps.addBatch();
            }

            ps.executeBatch(); // 배치 실행
        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 처리 로직 추가
        }
    }

    public void batchUpsertDinerPhotos(List<DinerPhoto> dinerPhotos) {
        String confirmIdCheckSql = "SELECT COUNT(*) FROM diner_detail WHERE confirm_id = ?";
        String sql = "INSERT INTO diner_photo (confirm_id, photo_url) " +
                "VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "photo_url = VALUES(photo_url)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement checkPs = conn.prepareStatement(confirmIdCheckSql);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (DinerPhoto dinerPhoto : dinerPhotos) {
                checkPs.setLong(1, dinerPhoto.getConfirmId());
                try (ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        ps.setLong(1, dinerPhoto.getConfirmId());
                        ps.setString(2, dinerPhoto.getPhotoUrl());
                        ps.addBatch();
                    } else {
                        // confirm_id가 존재하지 않을 경우 처리
                        System.out.println("confirm_id " + dinerPhoto.getConfirmId() + "는 diner_detail에 존재하지 않습니다.");
                    }
                }
            }
            ps.executeBatch(); // 배치 실행
        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 처리 로직 추가
        }
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

            ps.executeBatch();
            ps.clearBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Upsert failed", e);
        }

    }

    public void upsertDinerDetails(List<DinerDetail> dinerDetails) throws SQLException {
        String sql = "INSERT INTO diner_detail (confirm_id, tel, main_photo, name, x, y, category1, category2, taste_rank, price_rank, mood_rank, kindness_rank, parking_rank) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE tel = VALUES(tel), main_photo = VALUES(main_photo), name = VALUES(name), " +
                "x = VALUES(x), y = VALUES(y), category1 = VALUES(category1), category2 = VALUES(category2), " +
                "taste_rank = VALUES(taste_rank), price_rank = VALUES(price_rank), mood_rank = VALUES(mood_rank), " +
                "kindness_rank = VALUES(kindness_rank), parking_rank = VALUES(parking_rank);";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (DinerDetail dinerDetail : dinerDetails.stream().filter(Objects::nonNull).toList()) {
                ps.setLong(1, dinerDetail.getConfirmId());
                ps.setString(2, dinerDetail.getTel());
                ps.setString(3, dinerDetail.getMainPhoto());
                ps.setString(4, dinerDetail.getName());
                ps.setInt(5, dinerDetail.getX());
                ps.setInt(6, dinerDetail.getY());
                ps.setString(7, dinerDetail.getCategory1());
                ps.setString(8, dinerDetail.getCategory2());
                ps.setInt(9, dinerDetail.getTasteRank());
                ps.setInt(10, dinerDetail.getPriceRank());
                ps.setInt(11, dinerDetail.getMoodRank());
                ps.setInt(12, dinerDetail.getKindnessRank());
                ps.setInt(13, dinerDetail.getParkingRank());

                ps.addBatch();
            }

            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Upsert failed", e);
        }
    }



}
