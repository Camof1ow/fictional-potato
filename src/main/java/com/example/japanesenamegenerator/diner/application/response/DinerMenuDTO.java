package com.example.japanesenamegenerator.diner.application.response;

import com.example.japanesenamegenerator.diner.domain.DinerMenu;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class DinerMenuDTO {

    private String name;
    private String description;
    private Integer price;
    private boolean recommend;

    @Builder
    public DinerMenuDTO(Long id, String name, String description, Integer price, boolean recommend) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.recommend = recommend;
    }

    public static List<DinerMenuDTO> getListFrom(List<DinerMenu> dinerMenus) {
        return dinerMenus.stream().map(dinerMenu -> DinerMenuDTO.builder()
                .name(dinerMenu.getName())
                .description(dinerMenu.getDesc())
                .price(dinerMenu.getPrice())
                .recommend(dinerMenu.isRecommend())
                .build()).toList();
    }
}
