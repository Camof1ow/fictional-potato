package com.example.japanesenamegenerator.diner.application.response;

import com.example.japanesenamegenerator.diner.domain.DinerInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DinerInfoResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String tel;
    private String image;
    private Long confirmId;

    private String lastCategoryName;
    private String categoryDepth1;
    private String categoryDepth2;
    private String categoryDepth3;
    private String categoryDepth4;
    private String categoryDepth5;
    private int x;
    private int y;
    private double ratingAverage;
    private int ratingCount;
    private int reviewCount;
    private double lon;
    private double lat;
    private String addinfoAppointment;
    private String addinfoDelivery;
    private String addinfoFordisabled;
    private String addinfoNursery;
    private String addinfoPackage;
    private String addinfoParking;
    private String addinfoPet;
    private String addinfoSmokingroom;
    private String addinfoWifi;

    @Builder
    public DinerInfoResponseDTO(Long id,
                                String name,
                                String address,
                                String tel,
                                String image,
                                String lastCategoryName,
                                String categoryDepth1,
                                String categoryDepth2,
                                String categoryDepth3,
                                String categoryDepth4,
                                String categoryDepth5,
                                int x,
                                int y,
                                double ratingAverage,
                                int ratingCount,
                                int reviewCount,
                                double lon,
                                double lat,
                                String addinfoAppointment,
                                String addinfoDelivery,
                                String addinfoFordisabled,
                                String addinfoNursery,
                                String addinfoPackage,
                                String addinfoParking,
                                String addinfoPet,
                                String addinfoSmokingroom,
                                String addinfoWifi,
                                Long confirmId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.image = image;
        this.lastCategoryName = lastCategoryName;
        this.categoryDepth1 = categoryDepth1;
        this.categoryDepth2 = categoryDepth2;
        this.categoryDepth3 = categoryDepth3;
        this.categoryDepth4 = categoryDepth4;
        this.categoryDepth5 = categoryDepth5;
        this.x = x;
        this.y = y;
        this.ratingAverage = ratingAverage;
        this.ratingCount = ratingCount;
        this.reviewCount = reviewCount;
        this.lon = lon;
        this.lat = lat;
        this.addinfoAppointment = addinfoAppointment;
        this.addinfoDelivery = addinfoDelivery;
        this.addinfoFordisabled = addinfoFordisabled;
        this.addinfoNursery = addinfoNursery;
        this.addinfoPackage = addinfoPackage;
        this.addinfoParking = addinfoParking;
        this.addinfoPet = addinfoPet;
        this.addinfoSmokingroom = addinfoSmokingroom;
        this.addinfoWifi = addinfoWifi;
        this.confirmId = confirmId;
    }

    public static DinerInfoResponseDTO from(DinerInfo dinerInfo) {
        Long id = dinerInfo.getId();
        String name = dinerInfo.getName();
        String address = dinerInfo.getAddress();
        String tel = dinerInfo.getTel();
        String image = dinerInfo.getImage();
        String lastCategoryName = dinerInfo.getLastCategoryName();
        String categoryDepth1 = dinerInfo.getCategoryDepth1();
        String categoryDepth2 = dinerInfo.getCategoryDepth2();
        String categoryDepth3 = dinerInfo.getCategoryDepth3();
        String categoryDepth4 = dinerInfo.getCategoryDepth4();
        String categoryDepth5 = dinerInfo.getCategoryDepth5();
        int x = dinerInfo.getX();
        int y = dinerInfo.getY();
        double ratingAverage = dinerInfo.getRatingAverage();
        int ratingCount = dinerInfo.getRatingCount();
        int reviewCount = dinerInfo.getReviewCount();
        double lon = dinerInfo.getLon();
        double lat = dinerInfo.getLat();
        String addinfoAppointment = dinerInfo.getAddinfoAppointment();
        String addinfoDelivery = dinerInfo.getAddinfoDelivery();
        String addinfoFordisabled = dinerInfo.getAddinfoFordisabled();
        String addinfoNursery = dinerInfo.getAddinfoNursery();
        String addinfoPackage = dinerInfo.getAddinfoPackage();
        String addinfoParking = dinerInfo.getAddinfoParking();
        String addinfoPet = dinerInfo.getAddinfoPet();
        String addinfoSmokingroom = dinerInfo.getAddinfoSmokingroom();
        String addinfoWifi = dinerInfo.getAddinfoWifi();
        Long confirmId = Long.valueOf(dinerInfo.getConfirmId());

        return DinerInfoResponseDTO.builder()
                .id(id)
                .name(name)
                .address(address)
                .tel(tel)
                .image(image)
                .lastCategoryName(lastCategoryName)
                .categoryDepth1(categoryDepth1)
                .categoryDepth2(categoryDepth2)
                .categoryDepth3(categoryDepth3)
                .categoryDepth4(categoryDepth4)
                .categoryDepth5(categoryDepth5)
                .x(x)
                .y(y)
                .ratingAverage(ratingAverage)
                .ratingCount(ratingCount)
                .reviewCount(reviewCount)
                .lon(lon)
                .lat(lat)
                .addinfoAppointment(addinfoAppointment)
                .addinfoDelivery(addinfoDelivery)
                .addinfoFordisabled(addinfoFordisabled)
                .addinfoNursery(addinfoNursery)
                .addinfoPackage(addinfoPackage)
                .addinfoParking(addinfoParking)
                .addinfoPet(addinfoPet)
                .addinfoSmokingroom(addinfoSmokingroom)
                .addinfoWifi(addinfoWifi)
                .confirmId(confirmId)
                .build();
    }



}
