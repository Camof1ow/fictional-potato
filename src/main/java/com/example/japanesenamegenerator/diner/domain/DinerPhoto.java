package com.example.japanesenamegenerator.diner.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Table(name = "diner_photo")
@RequiredArgsConstructor
public class DinerPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "confirm_id")
    private Long confirmId; // DinerDetail의 confirmId와 연결

    @Column(name = "photo_url")
    private String photoUrl;

    @Builder
    public DinerPhoto(String photoUrl, Long confirmId) {
        this.photoUrl = photoUrl;
        this.confirmId = confirmId; // 생성자에서 confirmId 초기화
    }
}
