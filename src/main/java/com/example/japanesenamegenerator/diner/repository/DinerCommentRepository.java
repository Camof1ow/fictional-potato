package com.example.japanesenamegenerator.diner.repository;

import com.example.japanesenamegenerator.diner.domain.DinerComment;
import feign.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DinerCommentRepository extends CrudRepository<DinerComment, Long> {

    @Query("SELECT d.id FROM DinerComment d where d.username is null")
    List<Long> findAllIdByUsernameIsNull();

    @Modifying
    @Query("UPDATE DinerComment d SET d.username = :username WHERE d.id = :id")
    void updateUsername(@Param("id") Long id, @Param("username") String username);

    List<DinerComment> findAllByConfirmId(Long confirmId);
}
