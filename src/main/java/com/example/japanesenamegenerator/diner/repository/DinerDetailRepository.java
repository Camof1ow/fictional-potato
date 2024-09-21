package com.example.japanesenamegenerator.diner.repository;

import com.example.japanesenamegenerator.diner.domain.DinerDetail;
import feign.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DinerDetailRepository extends CrudRepository<DinerDetail, Long> {

    Boolean existsByConfirmId(Long confirmId);

    Optional<DinerDetail> findByConfirmId(Long confirmId);

    @Query("SELECT d.confirmId FROM DinerDetail d")
    List<String> findAllConfirmIds();

}
