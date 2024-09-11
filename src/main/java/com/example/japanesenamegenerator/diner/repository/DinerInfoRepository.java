package com.example.japanesenamegenerator.diner.repository;

import com.example.japanesenamegenerator.diner.domain.DinerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import feign.Param;


import java.util.List;

public interface DinerInfoRepository extends JpaRepository<DinerInfo, Integer> {


    @Query("SELECT d.confirmId FROM DinerInfo d")
    List<String> findAllConfirmIds();


    void deleteByConfirmId(String confirmId);

    @Query("SELECT d FROM DinerInfo d WHERE d.lon BETWEEN :lon1 AND :lon2 AND d.lat BETWEEN :lat1 AND :lat2")
    List<DinerInfo> findAllByCoordinate(@Param("lon1") Double lon1, @Param("lon2") Double lon2, @Param("lat1") Double lat1, @Param("lat2") Double lat2);

}
