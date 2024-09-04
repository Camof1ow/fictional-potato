package com.example.japanesenamegenerator.diner.domain;

import com.example.japanesenamegenerator.diner.application.response.PlaceData.Place;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Table(name = "diner_info")
@RequiredArgsConstructor
public class DinerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String tel;
    private String image;

    @Column(name = "last_cate_name")
    private String lastCategoryName;
    @Column(name = "category_depth1")
    private String categoryDepth1;
    @Column(name = "category_depth2")
    private String categoryDepth2;
    @Column(name = "category_depth3")
    private String categoryDepth3;
    @Column(name = "category_depth4")
    private String categoryDepth4;
    @Column(name = "category_depth5")
    private String categoryDepth5;
    @Column(name = "confirm_id", unique = true)
    private String confirmId;
    private int x;
    private int y;
    @Column(name = "rating_average")
    private double ratingAverage;
    @Column(name = "rating_count")
    private int ratingCount;
    @Column(name = "review_count")
    private int reviewCount;
    private double lon;
    private double lat;
    @Column(name = "appointment")
    private String addinfoAppointment;
    @Column(name = "delivery")
    private String addinfoDelivery;
    @Column(name = "for_disabled")
    private String addinfoFordisabled;
    @Column(name = "nursery")
    private String addinfoNursery;
    @Column(name = "package")
    private String addinfoPackage;
    @Column(name = "parking")
    private String addinfoParking;
    @Column(name = "pet")
    private String addinfoPet;
    @Column(name = "smoking_room")
    private String addinfoSmokingroom;
    @Column(name = "wifi")
    private String addinfoWifi;
//
//    @Column(columnDefinition = "json",name = "time_list")
//    @Convert(converter = TimeListConverter.class)
//    private List<TimeObject> timeList;
//
//    public static class TimeListConverter implements AttributeConverter<List<TimeObject>, String> {
//        private final ObjectMapper objectMapper = new ObjectMapper();
//
//        @Override
//        public String convertToDatabaseColumn(List<TimeObject> attribute) {
//            try {
//                return objectMapper.writeValueAsString(attribute);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException("Failed to convert List<TimeObject> to JSON string.", e);
//            }
//        }
//
//        @Override
//        public List<TimeObject> convertToEntityAttribute(String dbData) {
//            try {
//                return objectMapper.readValue(dbData, objectMapper.getTypeFactory().constructCollectionType(List.class, TimeObject.class));
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to convert JSON string to List<TimeObject>.", e);
//            }
//        }
//    }


    public DinerInfo(Place place){
        this.name = place.getName();
        this.address = place.getNew_address();
        this.tel = place.getTel();
        this.image = place.getImg();

        this.lastCategoryName = place.getLast_cate_name();
        this.categoryDepth1 = place.getCate_name_depth1();
        this.categoryDepth2 = place.getCate_name_depth2();
        this.categoryDepth3 = place.getCate_name_depth3();
        this.categoryDepth4 = place.getCate_name_depth4();
        this.categoryDepth5 = place.getCate_name_depth5();

        this.confirmId = place.getConfirmid();
        this.x = place.getX();
        this.y = place.getY();

        this.ratingAverage = place.getRating_average();
        this.ratingCount = place.getRating_count();
        this.reviewCount = place.getReviewCount();

        this.lon = place.getLon();
        this.lat = place.getLat();
        this.addinfoAppointment = place.getAddinfo_appointment();
        this.addinfoDelivery = place.getAddinfo_delivery();
        this.addinfoFordisabled = place.getAddinfo_fordisabled();
        this.addinfoNursery = place.getAddinfo_nursery();
        this.addinfoPackage = place.getAddinfo_package();
        this.addinfoParking = place.getAddinfo_parking();
        this.addinfoPet = place.getAddinfo_pet();
        this.addinfoSmokingroom = place.getAddinfo_smokingroom();
        this.addinfoWifi = place.getAddinfo_wifi();
//        this.timeList = new ArrayList<>();
//        List<Place.OpenOff.PeriodList> periodList = place.getOpenoff().getPeriodList();
//        if(!periodList.isEmpty()){
//            periodList.forEach( pl -> timeList.addAll(pl.getTimeList()));
//        }

    }

}
