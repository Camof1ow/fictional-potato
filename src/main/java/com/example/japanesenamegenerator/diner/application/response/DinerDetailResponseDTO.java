package com.example.japanesenamegenerator.diner.application.response;

import com.example.japanesenamegenerator.diner.domain.DinerComment;
import com.example.japanesenamegenerator.diner.domain.DinerDetail;
import com.example.japanesenamegenerator.diner.domain.DinerMenu;
import com.example.japanesenamegenerator.diner.domain.DinerPhoto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class DinerDetailResponseDTO {

    private Long id;
    private long confirmId;
    private String tel;
    private String mainPhoto;
    private String name;
    private DinerDetail.Address address;
    private int x;
    private int y;
    private String category1;
    private String category2;
    private int tasteRank;
    private int priceRank;
    private int moodRank;
    private int kindnessRank;
    private int parkingRank;

    private List<String> photoList;
    private List<DinerMenuDTO> menuList;
    private List<DinerCommentResponseDTO> commentList;

    @Builder
    public DinerDetailResponseDTO(Long id,
                                  long confirmId,
                                  String tel,
                                  String mainPhoto,
                                  String name,
                                  DinerDetail.Address address,
                                  int x,
                                  int y,
                                  String category1,
                                  String category2,
                                  int tasteRank,
                                  int priceRank,
                                  int moodRank,
                                  int kindnessRank,
                                  int parkingRank,
                                  List<String> photoList,
                                  List<DinerMenuDTO> menuList,
                                  List<DinerCommentResponseDTO> commentList) {
        this.id = id;
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
        this.photoList = photoList;
        this.menuList = menuList;
        this.commentList = commentList;

    }

    public static DinerDetailResponseDTO from(DinerDetail dinerDetail, List<DinerComment> commentList, List<DinerMenu> dinerMenuList, List<DinerPhoto> photoList ){

        List<String> photoStringList = photoList.stream().map(DinerPhoto::getPhotoUrl).toList();

        return DinerDetailResponseDTO.builder()
                .id(dinerDetail.getId())
                .confirmId(dinerDetail.getConfirmId())
                .tel(dinerDetail.getTel())
                .mainPhoto(dinerDetail.getMainPhoto())
                .name(dinerDetail.getName())
                .address(dinerDetail.getAddress())
                .x(dinerDetail.getX())
                .y(dinerDetail.getY())
                .category1(dinerDetail.getCategory1())
                .category2(dinerDetail.getCategory2())
                .tasteRank(dinerDetail.getTasteRank())
                .priceRank(dinerDetail.getPriceRank())
                .moodRank(dinerDetail.getMoodRank())
                .kindnessRank(dinerDetail.getKindnessRank())
                .parkingRank(dinerDetail.getParkingRank())
                .photoList(photoStringList)
                .menuList(DinerMenuDTO.getListFrom(dinerMenuList))
                .commentList(DinerCommentResponseDTO.getListFrom(commentList))
                .build();
    }

}
