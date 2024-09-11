package com.example.japanesenamegenerator.diner.application.response;

import com.example.japanesenamegenerator.diner.domain.DinerComment;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Getter
@RequiredArgsConstructor
public class DinerCommentResponseDTO {

    private Long id;
    private String username;
    private String contents;
    private List<String> photoList;
    private String profile;
    private String date;
    private String thumbnail;

    @Builder
    public DinerCommentResponseDTO(Long id,
                                   String username,
                                   String contents,
                                   List<String> photoList,
                                   String profile,
                                   String date,
                                   String thumbnail) {
        this.id = id;
        this.username = username;
        this.contents = contents;
        this.photoList = photoList;
        this.profile = profile;
        this.date = date;
        this.thumbnail = thumbnail;
    }

    public static List<DinerCommentResponseDTO> getListFrom(List<DinerComment> list) {

        if(list == null || list.isEmpty()) {
            return null;
        }else {
            return list.stream().map(dinerComment ->
                    DinerCommentResponseDTO.builder()
                            .id(dinerComment.getId())
                            .username(dinerComment.getUsername())
                            .contents(dinerComment.getContents())
                            .photoList(dinerComment.getPhotoList())
                            .profile(dinerComment.getProfile())
                            .date(dinerComment.getDate())
                            .thumbnail(dinerComment.getThumbnail())
                            .build()
            ).toList();
        }
    }
}
