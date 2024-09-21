package com.example.japanesenamegenerator.diner.repository;

import com.example.japanesenamegenerator.diner.domain.DinerMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DinerMenuRepository extends JpaRepository<DinerMenu, Integer> {

    List<DinerMenu> findAllByConfirmId(Long confirmId);
}
