package com.example.japanesenamegenerator.diner.repository;

import com.example.japanesenamegenerator.diner.domain.DinerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface DinerInfoRepository extends JpaRepository<DinerInfo, Integer> {


    @Query("SELECT d.confirmId FROM DinerInfo d")
    List<String> findAllConfirmIds();


    void deleteByConfirmId(String confirmId);

}
