package com.example.japanesenamegenerator.diner.application.response;

import com.example.japanesenamegenerator.diner.domain.DinerComment;
import com.example.japanesenamegenerator.diner.domain.DinerDetail;
import com.example.japanesenamegenerator.diner.domain.DinerDetail.Menu;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDetailDTO {

    private boolean isExist;
    private String isMapUser;

    private BasicInfo basicInfo;
    private S2Graph s2Graph;
    private Review comment;
    private FindWay findway;
    private PlaceOwnerInfos placeOwnerInfos;
    private TrendRank trendRank;
    private MenuInfo menuInfo;
    private Photo photo;

    @Getter
    public static class Photo {
        private List<PhotoObject> photoList;

        @Getter
        public static class PhotoObject {
            private String categoryName;
            private int photoCount;
            private List<PhotoUrl> list;
        }

        @Getter
        public static class PhotoUrl {
            private String orgurl;
            private String photoid;
        }
    }

    @Getter
    public static class MenuInfo {
        private int menuboardphotocount;
        private List<String> menuboardphotourlList;
        private List<Menu> menuList;
        private int menucount;
        private String productyn;

        @Getter
        public static class Menu {
            private String desc;
            private String menu;
            private String price;
            private boolean recommend;
        }

    }

    @Getter
    public static class TrendRank {
        private Meta meta;
        private List<Group> groups;
    }

    @Getter
    public static class Group {
        private int analyzed_at;
        private String type;
        private List<Item> items;
    }

    @Getter
    public static class Item {
        private AnalyzedInfo analyzed_info;
        private List<Region> regions;
        private String category_name;
        private int confirm_id;
        private int lat;
        private int lon;
        private String name;
        private int rank;

        private int review_count;
        private double review_rating;
        private String review_status;
        private String thumbnail;

        @Getter
        public static class Region {
            private int depth;
            private String id;
            private String name;
        }

        @Getter
        public static class AnalyzedInfo {
            private int click_count_increase_rate;
            private int dest_count_increase_rate;
            private boolean hot_rank;
            private int rank_change;
        }
    }


    @Getter
    public static class Meta {
        private List<CateOrMenu> category_or_menus;
        private List<String> depth1_regions_for_show_depth3_region;
    }

    @Getter
    public static class CateOrMenu {
        private String value;
        private String name;
        private String description;
    }

    @Getter
    public static class PlaceOwnerInfos {
        private String status;
        private String loginUserRelation;
    }


    @Getter
    public static class FindWay {
        private int x;
        private int y;
        private List<BusStop> busstop;
        private boolean busDirectionCheck;
    }

    @Getter
    public static class BusStop {
        private String busStopId;
        private String busStopName;
        private String busStopDisplayId;
        private int toBusstopDistance;
        private int wpointx;
        private int wpointy;
        private List<BusInfo> busInfo;
    }

    @Getter
    public static class BusInfo {
        private String busType;
        private String busTypeCode;
        private List<Bus> busList;
        private String busNames;
    }

    @Getter
    public static class Bus {
        private String busId;
        private String busName;
        private String busTextName; // Optional, only in some buses
    }

    @Getter
    public static class Review {
        private String placenamefull;
        private int kamapComntcnt;
        private int scoresum;
        private int scorecnt;
        private List<Comment> list;
        private List<StrengthCount> strengthCounts;
        private boolean hasNext;
        private String reviewWriteBlocked;
    }

    @Getter
    public static class Comment {
        private String commentid;
        private String contents;
        private int point;
        private String username;
        private String profile;
        private String profileStatus;
        private int photoCnt;
        private int likeCnt;
        private String kakaoMapUserId;
        private List<Photo> photoList;
        private int userCommentCount;
        private double userCommentAverageScore;
        private boolean myStorePick;
        private Level level;
        private String date;
        private boolean isMy;
        private boolean isBlock;
        private boolean isEditable;
        private boolean isMyLike;
        private List<Strength> strengths;  // Optional, only in some comments
        private String thumbnail; // Optional, only in some comments

        @Getter
        public static class Photo {
            private String url;
            private boolean near;
        }

        @Getter
        public static class Strength {
            private int id;
            private String name;
        }

        @Getter
        public static class Level {
            private int nowLevel;
            private String badge;
        }
    }

    @Getter
    public static class StrengthCount {
        private int id;
        private String name;
        private int count;
    }

    @Getter
    public static class S2Graph {
        public String status;
        public Day day;
        public Gender gender;
        public Age age;
    }

    @Getter
    public static class Gender {
        public List<String> labels;
        public List<Integer> data;
        public int max;
    }


    @Getter
    public static class Day {
        public List<Integer> initData;
        public List<String> labels;
        public List<Integer> avg;
        public List<Integer> sunday;
        public List<Integer> monday;
        public List<Integer> tuesday;
        public List<Integer> wednesday;
        public List<Integer> thursday;
        public List<Integer> friday;
        public List<Integer> saturday;
        public int max;
    }

    @Getter
    public static class Age {
        public List<String> labels;
        public List<Integer> data;
        public int max;
    }

    @Getter
    public static class BasicInfo {
        public long cid;
        public String placenamefull;
        public String mainphotourl;
        public String phonenum;
        public Address address;
        public int wpointx;
        public int wpointy;
        public Roadview roadview;
        public Category category;
        public Feedback feedback;
    }

    @Getter
    public static class Address {
        public NewAddr newaddr;
        public Region region;
        public String addrbunho;
        public String addrdetail;
    }

    @Getter
    public static class NewAddr {
        public String newaddrfull;
        public String bsizonno;
    }

    @Getter
    public static class Region {
        public String name3;
        public String fullname;
        public String newaddrfullname;
    }

    @Getter
    public static class Roadview {
        public long panoid;
        public int tilt;
        public double pan;
        public int wphotox;
        public int wphotoy;
        public int rvlevel;
    }

    @Getter
    public static class Category {
        public String cateid;
        public String catename;
        public String cate1name;
        public String fullCateIds;
    }

    @Getter
    public static class Feedback {
        public int allphotocnt;
        public int blogrvwcnt;
        public int comntcnt;
        public int scoresum;
        public int scorecnt;
    }

    @Getter
    public static class BlogReview {
        private String placenamefull;
        private int moreId;
        private int blogrvwcnt;
        private List<BlogPost> list;
    }

    @Getter
    public static class BlogPost {
        private int id;
        private String title;
        private String content;
        private List<Photo> photos;

        @Getter
        public static class Photo {
            private String url;
            private String description;
        }

    }

    public List<DinerComment> toDinerComments() {
        Review comment = this.getComment();
        BasicInfo basicInfo = this.getBasicInfo();
        if(comment == null || comment.getList() == null || comment.getList().isEmpty()){
            return null;
        }

        return comment.getList().stream().map(com -> DinerComment.builder()
                .confirmId(basicInfo.getCid())
                .username(com.getUsername())
                .contents(com.getContents())
                .photoList(com.getPhotoList().stream()
                        .map(Comment.Photo::getUrl).toList())
                .profile(com.getProfile())
                .date(com.getDate())
                .thumbnail(com.getThumbnail())
                .build()).toList();
    }

    public DinerDetail toDetailEntity() {

        BasicInfo basicInfo = this.getBasicInfo();
        List<StrengthCount> strengthCounts = this.getComment().getStrengthCounts();
        StrengthCount taste = strengthCounts.stream().filter(strengthCount -> strengthCount.id == 5).findFirst().orElseGet(() -> null);
        StrengthCount price = strengthCounts.stream().filter(strengthCount -> strengthCount.id == 1).findFirst().orElseGet(() -> null);
        StrengthCount kindness = strengthCounts.stream().filter(strengthCount -> strengthCount.id == 2).findFirst().orElseGet(() -> null);
        StrengthCount mood = strengthCounts.stream().filter(strengthCount -> strengthCount.id == 3).findFirst().orElseGet(() -> null);
        StrengthCount parking = strengthCounts.stream().filter(strengthCount -> strengthCount.id == 4).findFirst().orElseGet(() -> null);

        int tasteRank = taste != null ? taste.getCount() : 0;
        int priceRank = price != null ? price.getCount() : 0;
        int kindnessRank = kindness != null ? kindness.getCount() : 0;
        int moodRank = mood != null ? mood.getCount() : 0;
        int parkingRank = parking != null ? parking.getCount() : 0;
        List<Menu> menuList = new ArrayList<>();
        if (this.getMenuInfo() != null) {
            menuList = this.getMenuInfo().getMenuList().stream().map(
                    menu -> {
                        Integer menuPrice = null;
                        if (menu.getPrice() != null) {
                            menuPrice = Integer.parseInt(menu.getPrice().replaceAll("[^0-9]", ""));
                        }

                        return Menu.builder()
                                .desc(menu.getDesc())
                                .name(menu.getMenu())
                                .price(menuPrice)
                                .recommend(menu.isRecommend())
                                .build();
                    }
            ).toList();
        }

        List<String> photoList = new ArrayList<>();
        if (this.getPhoto() != null) {
            photoList = this.getPhoto().getPhotoList().getFirst().getList().stream()
                    .map(Photo.PhotoUrl::getOrgurl).toList();
        }

        return DinerDetail.builder()
                .confirmId(basicInfo.getCid())
                .tel(basicInfo.getPhonenum())
                .mainPhoto(basicInfo.getMainphotourl())
                .name(basicInfo.getPlacenamefull())
                .address(DinerDetail.Address.builder()
                        .newAddress(basicInfo.getAddress().getNewaddr().getNewaddrfull())
                        .zipCode(basicInfo.getAddress().getNewaddr().getBsizonno())
                        .addressDepth1(basicInfo.getAddress().getRegion().getNewaddrfullname())
                        .addressDepth2(basicInfo.getAddress().getRegion().getName3())
                        .addressDepth3(basicInfo.getAddress().getAddrbunho())
                        .detail(basicInfo.getAddress().getAddrdetail())
                        .build())
                .x(basicInfo.getWpointx())
                .y(basicInfo.getWpointy())
                .tasteRank(tasteRank)
                .priceRank(priceRank)
                .moodRank(moodRank)
                .kindnessRank(kindnessRank)
                .parkingRank(parkingRank)
                .category1(basicInfo.getCategory().getCate1name())
                .category2(basicInfo.getCategory().getCatename())
                .menuList(menuList)
                .photoList(photoList)
                .build();

    }


}
