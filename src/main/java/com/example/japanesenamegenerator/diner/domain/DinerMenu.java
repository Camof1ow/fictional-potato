package com.example.japanesenamegenerator.diner.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Table(name = "diner_menu")
@RequiredArgsConstructor
public class DinerMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "confirm_id")
    private Long confirmId; // DinerDetail의 confirmId와 연결

    @Column(name = "description")
    private String desc;
    private String name;
    private Integer price;
    private boolean recommend;

    @Builder
    public DinerMenu(boolean recommend, Integer price, String name, String desc, Long confirmId) {
        this.recommend = recommend;
        this.price = price;
        this.name = name;
        this.desc = desc;
        this.confirmId = confirmId; // 생성자에서 confirmId 초기화
    }
}
