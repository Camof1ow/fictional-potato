package com.example.japanesenamegenerator.sample.repository;

import com.example.japanesenamegenerator.sample.domain.DinerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DinerInfoRepository extends JpaRepository<DinerInfo, Integer> {


    @Query("SELECT d.confirmId FROM DinerInfo d")
    List<String> findAllConfirmIds();

}
