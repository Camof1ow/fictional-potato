package com.example.japanesenamegenerator.diner.domain;

import com.example.japanesenamegenerator.diner.entityconverter.StringListConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "diner_comment")
@RequiredArgsConstructor
public class DinerComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="confirm_id")
    private long confirmId;

    private String username;
    private String contents;

    @Convert(converter = StringListConverter.class)
    private List<String> photoList;

    private String profile;
    private String date;
    private String thumbnail;

    @Builder
    public DinerComment(long confirmId, String username, String contents, List<String> photoList, String profile,
                        String date, String thumbnail) {
        this.confirmId = confirmId;
        this.username = username;
        this.contents = contents;
        this.photoList = photoList;
        this.profile = profile;
        this.date = date;
        this.thumbnail = thumbnail;
    }
}
