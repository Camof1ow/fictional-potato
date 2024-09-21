package com.example.japanesenamegenerator.diner.repository;

import com.example.japanesenamegenerator.diner.domain.DinerMenu;
import com.example.japanesenamegenerator.diner.domain.DinerPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DinerPhotoRepository extends JpaRepository<DinerPhoto, Long> {
    List<DinerPhoto> findAllByConfirmId(Long confirmId);
}
