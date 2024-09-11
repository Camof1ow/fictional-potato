package com.example.japanesenamegenerator.diner.repository;

import com.example.japanesenamegenerator.diner.domain.DinerDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DinerDetailRepository extends CrudRepository<DinerDetail, Long> {

    Boolean existsByConfirmId(Long confirmId);

    Optional<DinerDetail> findByConfirmId(Long confirmId);
}
