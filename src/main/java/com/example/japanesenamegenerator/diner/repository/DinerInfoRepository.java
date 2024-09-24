package com.example.japanesenamegenerator.diner.repository;

import com.example.japanesenamegenerator.diner.domain.DinerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface DinerInfoRepository extends JpaRepository<DinerInfo, Long> {


    @Query("SELECT d.confirmId FROM DinerInfo d")
    List<Long> findAllConfirmIds();

    Boolean existsByConfirmId(Long confirmId);
    void deleteByConfirmId(Long confirmId);

    @Query("SELECT c.confirmId " +
            "FROM DinerInfo c " +
            "LEFT JOIN DinerDetail d ON c.confirmId = d.confirmId " +
            "WHERE d.id IS NULL")
    List<Long> findAllConfirmIdsWithoutDetail();

}
