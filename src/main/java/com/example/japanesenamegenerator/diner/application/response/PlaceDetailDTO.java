package com.example.japanesenamegenerator.diner.application.response;

import com.example.japanesenamegenerator.diner.domain.DinerComment;
import com.example.japanesenamegenerator.diner.domain.DinerDetail;
import com.example.japanesenamegenerator.diner.domain.DinerDetail.Menu;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

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

    public static String getRandomNickName() {
        List<String> adv = List.of("가끔", "가만히", "갑자기", "거의", "그냥", "급히", "간단히", "광범위하게", "구체적으로", "그다지", "그리", "금방", "기본적으로", "깔끔하게", "개별적으로", "그대로", "조용히", "가벼운", "정확히", "다시", "다소", "다양하게", "당연히", "덜", "단순히", "대부분", "드물게", "매우", "매번", "무조건", "미리", "바로", "빠르게", "빛나게", "부드럽게", "분명히", "불확실하게", "상당히", "세밀하게", "시시각각", "심각하게", "아주", "알뜰하게", "어떻게든", "언제든지", "엄청", "우연히", "원래", "자연스럽게", "자주", "잘", "정확하게", "조금", "조용히", "주로", "중요하게", "지속적으로", "직접", "조속히", "적당히", "천천히", "확실히", "하나씩", "혹시", "편안히", "평소", "포괄적으로", "프라이버시", "불행히", "성실히", "위험하게", "유명하게", "잘못", "조금씩", "천천히", "고의로", "신속히", "은밀히", "당장", "완전히", "의도적으로", "가장", "막연히", "비교적", "역시", "즉시", "운 좋게", "불가피하게", "노력하여", "극도로", "화려하게", "사실", "심각히", "편리하게", "편안하게", "변별력 있게", "고정적으로", "유용하게", "차분히", "어느 정도", "의도적으로", "시급히", "최대한", "매일", "지속적으로", "느긋하게", "상세히", "지속적으로", "이상", "비교적", "부드럽게", "조속히", "효율적으로", "계속해서", "꼼꼼히", "상세하게", "활발히", "차별화되게", "의외로", "완전히", "신중하게", "분명히", "근본적으로", "기본적으로", "엄밀히", "차례로", "상시", "유연하게", "정기적으로", "전반적으로", "안정적으로", "점진적으로", "일관되게", "구체적으로", "편리하게", "직관적으로", "정성스럽게", "세심하게", "체계적으로", "철저히", "적절하게", "다양하게", "신중하게", "대체로", "깔끔하게", "차례로", "깊이", "체계적으로", "중점적으로", "적극적으로", "상세히", "무의식적으로", "적절히", "조심스럽게", "충분히", "전략적으로");
        List<String> adj = List.of("가냘픈", "가는", "가엾은", "가파른", "같은", "거센", "거친", "검은", "게으른", "고달픈", "고른", "고마운", "고운", "고픈", "곧은", "괜찮은", "구석진", "굳은", "굵은", "귀여운", "그런", "그른", "그리운", "기다란", "기쁜", "깊은", "깎아지른", "깨끗한", "나은", "난데없는", "날랜", "날카로운", "낮은", "너그러운", "너른", "널따란", "넓은", "네모난", "노란", "높은", "누런", "눅은", "느닷없는", "느린", "늦은", "다른", "더운", "덜된", "동그란", "둥그런", "둥근", "뒤늦은", "드문", "딱한", "때늦은", "뛰어난", "뜨거운", "막다른", "많은", "매운", "멋진", "메마른", "메스꺼운", "모난", "무거운", "무딘", "무른", "무서운", "미끄러운", "바람직한", "반가운", "밝은", "밤늦은", "보드라운", "보람찬", "부드러운", "붉은", "비싼", "빠른", "빨간", "뻘건", "뼈저린", "뽀얀", "뿌연", "새로운", "서툰", "섣부른", "설운", "성가신", "수줍은", "쉬운", "스스러운", "슬픈", "싼", "쌀쌀맞은", "쏜살같은", "쓰디쓴", "쓰린", "쓴", "아닌", "아름다운", "아쉬운", "아픈", "안쓰러운", "안타까운", "알맞은", "약빠른", "약은", "얇은", "얕은", "어두운", "어려운", "어린", "언짢은", "엄청난", "없는", "여문", "열띤", "예쁜", "올바른", "옳은", "외로운", "우스운", "의심스런", "이른", "익은", "있는", "작은", "잘난", "잘빠진", "잘생긴", "재미있는", "적은", "젊은", "점잖은", "조그만", "좁은", "좋은", "주제넘은", "줄기찬", "즐거운", "지나친", "지혜로운", "질긴", "짓궂은", "짙은", "짠", "짧은", "케케묵은", "큰", "탐스러운", "턱없는", "푸른", "한결같은", "흐린", "희망찬", "흰", "힘겨운");
        List<String> noun = List.of("사과", "배", "바나나", "오렌지", "포도", "딸기", "블루베리", "라즈베리", "복숭아", "자몽", "키위", "파인애플", "망고", "수박", "참외", "멜론", "석류", "귤", "레몬", "라임", "청포도", "자두", "플럼", "무화과", "아보카도", "배추", "상추", "시금치", "로메인", "케일", "미나리", "고추", "당근", "호박", "오이", "토마토", "양파", "마늘", "생강", "대파", "쪽파", "부추", "미역", "김", "콩나물", "버섯", "표고버섯", "양송이", "능이버섯", "송이버섯", "시래기", "겨자", "숙주", "순무", "파슬리", "고수", "로즈마리", "타임", "딜", "바질", "세이지", "오레가노", "피망", "주키니", "아스파라거스", "브로콜리", "콜리플라워", "양배추", "적양배추", "경단", "버터", "치즈", "요거트", "우유", "크림", "우유", "계란", "밀가루", "설탕", "소금", "후추", "올리브유", "식용유", "참기름", "간장", "된장", "고추장", "마요네즈", "케첩", "초콜릿", "흑설탕", "갈색설탕", "아몬드", "호두", "피스타치오", "캐슈넛", "해바라기씨", "호박씨", "치아씨드", "귀리", "조", "현미", "백미", "콩", "렌틸콩", "병아리콩", "강낭콩", "적두", "흑콩", "두부", "연두부", "된장", "간장", "된장", "쌀", "밀", "보리", "메밀", "진미채", "떡", "바게트", "크로아상", "팬케이크", "비스킷", "쿠키", "빵", "파스타", "면", "누들", "라면", "스파게티", "마카로니", "리조또", "햄", "소시지", "베이컨", "살라미", "칠면조", "닭고기", "소고기", "돼지고기", "양고기", "오리", "생선", "새우", "오징어", "조개", "홍합", "게", "랍스터", "연어", "참치", "광어", "고등어", "갈치", "다시마", "미역", "육수", "베이컨", "샐러리", "스프", "수프", "찜", "볶음", "조림", "구이", "튀김", "회", "샐러드");

        return String.format("%s%s%s", getRandomElement(adv), getRandomElement(adj), getRandomElement(noun));
    }


    public static String getRandomElement(List<String> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("리스트가 비어있거나 null입니다.");
        }

        Random random = new Random();
        int index = random.nextInt(list.size()); // 리스트의 크기 범위 내에서 랜덤 인덱스 생성
        return list.get(index);
    }

    public List<DinerComment> toDinerComments() {
        Review comment = this.getComment();
        BasicInfo basicInfo = this.getBasicInfo();
        if (comment == null || comment.getList() == null || comment.getList().isEmpty()) {
            return null;
        }

        return comment.getList().stream().map(com -> DinerComment.builder()
                .confirmId(basicInfo.getCid())
                .username(getRandomNickName())
                .contents(com.getContents())
                .photoList(com.getPhotoList().stream()
                        .map(Comment.Photo::getUrl).toList())
                .profile(com.getProfile())
                .date(com.getDate())
                .thumbnail(com.getThumbnail())
                .build()).toList();
    }

    public DinerDetail toDetailEntity() {
        try {
            BasicInfo basicInfo = this.getBasicInfo();
            if(basicInfo == null) {
                return null;
            }
            StrengthCount taste = null;
            StrengthCount price = null;
            StrengthCount kindness = null;
            StrengthCount mood = null;
            StrengthCount parking = null;

            if (this.getComment() != null) {
                List<StrengthCount> strengthCounts = this.getComment().getStrengthCounts();
                taste = strengthCounts.stream().filter(strengthCount -> strengthCount.id == 5).findFirst().orElseGet(() -> null);
                price = strengthCounts.stream().filter(strengthCount -> strengthCount.id == 1).findFirst().orElseGet(() -> null);
                kindness = strengthCounts.stream().filter(strengthCount -> strengthCount.id == 2).findFirst().orElseGet(() -> null);
                mood = strengthCounts.stream().filter(strengthCount -> strengthCount.id == 3).findFirst().orElseGet(() -> null);
                parking = strengthCounts.stream().filter(strengthCount -> strengthCount.id == 4).findFirst().orElseGet(() -> null);
            }


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
                            try {
                                menuPrice = Integer.parseInt(menu.getPrice().replaceAll("[^0-9]", ""));
                            } catch (Exception e) {
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
            String name = basicInfo.getPlacenamefull();

            boolean newAddrIsExist = basicInfo.getAddress().getNewaddr() != null;
            String newAddr = !newAddrIsExist ? null : basicInfo.getAddress().getNewaddr().getNewaddrfull();
            String zipcode = !newAddrIsExist ? null : basicInfo.getAddress().getNewaddr().getBsizonno();
            String full = basicInfo.getAddress().getRegion().getNewaddrfullname();


            return DinerDetail.builder()
                    .confirmId(basicInfo.getCid())
                    .tel(basicInfo.getPhonenum())
                    .mainPhoto(basicInfo.getMainphotourl())
                    .name(name.length() <= 100 ? name : name.substring(0, 100))
                    .address(DinerDetail.Address.builder()
                            .newAddress(newAddr)
                            .zipCode(zipcode)
                            .addressDepth1(full)
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

        } catch (Exception e) {
            return null;
        }


    }


}
