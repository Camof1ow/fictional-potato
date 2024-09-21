package com.example.japanesenamegenerator.diner.domain;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table(name = "diner_detail")
@RequiredArgsConstructor
public class DinerDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "confirm_id", unique = true)
    private long confirmId;
    private String tel;
    @Column(name = "main_photo")
    private String mainPhoto;
    private String name;
    @Embedded
    private Address address;

    @Embeddable
    @RequiredArgsConstructor
    @Builder
    public static class Address {

        @Column(name = "new_address") // 매핑할 컬럼 정의 가능
        private String newAddress;
        @Column(name = "zip_code") // 매핑할 컬럼 정의 가능
        private String zipCode;
        @Column(name = "address_depth1") // 매핑할 컬럼 정의 가능
        private String addressDepth1;
        @Column(name = "address_depth2") // 매핑할 컬럼 정의 가능
        private String addressDepth2;
        @Column(name = "address_depth3") // 매핑할 컬럼 정의 가능
        private String addressDepth3;
        @Column(name = "detail") // 매핑할 컬럼 정의 가능
        private String detail;
                                    
        @Builder
        public Address(String newAddress, String zipCode,
                       String addressDepth1, String addressDepth2,
                       String addressDepth3, String detail) {

            this.newAddress = newAddress;
            this.zipCode = zipCode;
            this.addressDepth1 = addressDepth1;
            this.addressDepth2 = addressDepth2;
            this.addressDepth3 = addressDepth3;
            this.detail = detail;
        }
    }

    private int x;
    private int y;
    private String category1;
    private String category2;


    @Column(name="taste_rank")
    private int tasteRank;
    @Column(name="price_rank")
    private int priceRank;
    @Column(name="mood_rank")
    private int moodRank;
    @Column(name="kindness_rank")
    private int kindnessRank;
    @Column(name="parking_rank")
    private int parkingRank;

    @Builder
    public DinerDetail(long confirmId, String tel, String mainPhoto,
                       String name,Address address, int x, int y,
                       String category1, String category2,
                       int tasteRank, int priceRank, int moodRank,
                       int kindnessRank, int parkingRank) {
        this.confirmId = confirmId;
        this.tel = tel;
        this.mainPhoto = mainPhoto;
        this.name = name;
        this.address = address;
        this.x = x;
        this.y = y;
        this.category1 = category1;
        this.category2 = category2;
        this.tasteRank = tasteRank;
        this.priceRank = priceRank;
        this.moodRank = moodRank;
        this.kindnessRank = kindnessRank;
        this.parkingRank = parkingRank;
    }
}
