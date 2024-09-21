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
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DinerQueryRepository{

    private final JPAQueryFactory queryFactory;

    public Page<DinerInfoResponseDTO> findAllByCoordinate(Double lon1, Double lon2, Double lat1, Double lat2, Pageable pageable) {
        QDinerInfo qDinerInfo = QDinerInfo.dinerInfo;

        QueryResults<DinerInfo> queryResults = queryFactory
                .select(new QDinerInfo(qDinerInfo))
                .from(qDinerInfo)
                .where(
                        qDinerInfo.lon.between(lon1,lon2),
                        qDinerInfo.lat.between(lat1,lat2)
                )
                .orderBy(QueryDslUtil.getSortedColumn(pageable.getSort(), qDinerInfo))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        List<DinerInfoResponseDTO> list = queryResults.getResults().stream().map(DinerInfoResponseDTO::from).toList();

        return new PageImpl<>(list, pageable, queryResults.getTotal());
    }


}
