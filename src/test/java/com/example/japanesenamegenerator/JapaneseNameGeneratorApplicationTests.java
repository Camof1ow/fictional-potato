package com.example.japanesenamegenerator;

import com.example.japanesenamegenerator.config.RetryOnTimeoutInterceptor;
import com.example.japanesenamegenerator.diner.application.DinerService;
import com.example.japanesenamegenerator.diner.application.response.PlaceData;
import com.example.japanesenamegenerator.diner.application.response.PlaceDetailDTO;
import com.example.japanesenamegenerator.diner.domain.*;
import com.example.japanesenamegenerator.diner.repository.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class JapaneseNameGeneratorApplicationTests {

    @Autowired
    private DinerInfoRepository dinerInfoRepository;
    @Autowired
    private DinerDetailRepository dinerDetailRepository;
    @Autowired
    private DinerCommentRepository dinerCommentRepository;
    @Autowired
    private DinerService dinerService;
//    @Autowired
//    private DinerMenuRepository dinerMenuRepository;
//    @Autowired
//    private DinerPhotoRepository dinerPhotoRepository;


    //    @Test
    void contextLoads() throws IOException {

        Map<String, String> map = getHeaders();
        int pageNo = 1;
        int collectedCount = 0;
        boolean flag = true;

        Set<String> rectangleCoordinates = generateSquares(489403, 1118325, 506583, 1143865);
        for (String rect : rectangleCoordinates) {
            do {
                String requestUrl = String.format("https://search.map.kakao.com/mapsearch/map.daum?page=%d&sort=0", pageNo);
                requestUrl += "&callback=jQuery18106682811413432699_1725244537609&q=%EC%8B%9D%EB%8B%B9&msFlag=S&mcheck=Y&rect=";
                requestUrl += rect;
                flag = true;
                try (Response response = requestGetWithHeaders(requestUrl, map)) {
                    if (!response.isSuccessful() || response.request().url().pathSegments().contains("500.ko.html")) {
                        map = getHeaders();
                        continue;
                    } else {
                        String string = response.body().string();
                        String responseBody = unWrapJsonString(string);

                        //todo: 응답 직렬화 -> 객체 -> db저장.
                        ObjectMapper objectMapper = new ObjectMapper()
                                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        PlaceData placeDto = objectMapper.readValue(responseBody, PlaceData.class);
                        System.out.println(placeDto.getPlace_totalcount());
                        //불러올게 없으면 다음 좌표로 이동.
                        if (placeDto.getPlace_totalcount() == 0) {
                            flag = false;
                            break;
                        }


                        List<DinerInfo> list = placeDto.getPlace().stream().map(DinerInfo::new).toList();
                        List<String> existingIds = dinerInfoRepository.findAllConfirmIds();

                        List<DinerInfo> filteredList = list.stream()
                                .filter(dinerInfo -> !existingIds.contains(dinerInfo.getConfirmId()))
                                .collect(Collectors.toList());

                        dinerInfoRepository.saveAll(filteredList);

                        pageNo++;
                        collectedCount += filteredList.size();

                    }

                }
            }
            while (flag);
            pageNo = 1;
        }

    }
    //https://place.map.kakao.com/880498914

    //    @Test
    void getDetail() {

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<String> existingIds = dinerInfoRepository.findAllConfirmIds();
        for (String id : existingIds) {
            if (!dinerDetailRepository.existsByConfirmId(Long.valueOf(id))) {
                String url = "https://place.map.kakao.com/main/v/" + id;
                try (Response response = requestGetWithHeaders(url, null)) {
                    String string = response.body().string();
                    PlaceDetailDTO placeDetailDTO = objectMapper.readValue(string, PlaceDetailDTO.class);
                    DinerDetail detail = placeDetailDTO.toDetailEntity();
                    if (detail != null) {
                        dinerDetailRepository.save(detail);
                    } else {
                        dinerService.deleteInfo(id);
                    }
                    PlaceDetailDTO.Review comment = placeDetailDTO.getComment();
                    if (comment != null && comment.getList() != null && !comment.getList().isEmpty()) {
                        List<DinerComment> comments = placeDetailDTO.toDinerComments();
                        if (comments != null) {
                            dinerCommentRepository.saveAll(comments);
                        }
                    }
                    System.out.println();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Test
    void testtest(){
            Long confirm_id = 303807L;



    }


//    @Test
//    void replaceName() {
//
//        dinerService.updateCommentUsername();
//    }

//    @Test
//    void replaceMenuEntity() {
//
//        List<String> allConfirmIds = dinerDetailRepository.findAllConfirmIds();
//
//        for (String id : allConfirmIds) {
//            DinerDetail byConfirmId = dinerDetailRepository.findByConfirmId(Long.valueOf(id)).get();
//            List<DinerDetail.Menu> menuList = byConfirmId.getMenuList();
//            if (menuList != null && !menuList.isEmpty()) {
//
//                List<DinerMenu> dinerMenuStream = menuList.stream().map(dinerDetailMenu ->
//                        DinerMenu.builder()
//                                .confirmId(byConfirmId.getConfirmId())
//                                .price(dinerDetailMenu.getPrice())
//                                .name(dinerDetailMenu.getName())
//                                .desc(dinerDetailMenu.getDesc())
//                                .recommend(dinerDetailMenu.isRecommend())
//                                .build()
//                ).toList();
//                dinerMenuRepository.saveAll(dinerMenuStream);
//            }
//        }
//
//
//
//        System.out.println();
//
//
//    }

//    @Test
//    void replaceList() {
//        List<String> allConfirmIds = dinerDetailRepository.findAllConfirmIds();
//        for (String id : allConfirmIds) {
//            DinerDetail byConfirmId = dinerDetailRepository.findByConfirmId(Long.valueOf(id)).get();
//            List<String> photoList = byConfirmId.getPhotoList();
//            if (photoList != null && !photoList.isEmpty()) {
//                List<DinerPhoto> list = photoList.stream().map(photo -> DinerPhoto.builder()
//                        .confirmId(Long.parseLong(id))
//                        .photoUrl(photo)
//                        .build()).toList();
//
//                dinerPhotoRepository.saveAll(list);
//            }
//        }
//    }



    private static String unWrapJsonString(String input) {
        int startIndex = input.indexOf('(');
        int endIndex = input.lastIndexOf(')');

        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return input.substring(startIndex + 1, endIndex).trim();
        } else {
            throw new IllegalArgumentException("Invalid JSON string");
        }
    }

    private static Response requestGetWithHeaders(String url, Map<String, String> header)
            throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(Duration.ofSeconds(15L))
                .readTimeout(Duration.ofSeconds(15L))
                .addInterceptor(new RetryOnTimeoutInterceptor(5, 15000))
                .build();

        Request.Builder builder = new Request.Builder().url(url).method("GET", null);
        if (header != null) {
            header.forEach(builder::addHeader);
        }
        Request request = builder.build();

        return client.newCall(request).execute();
    }

    private static Map<String, String> getHeaders() {
        String requestUrl = "https://stat.tiara.kakao.com/track?d=%7B%22sdk%22%3A%7B%22type%22%3A%22WEB%22%2C%22version%22%3A%221.1.33%22%7D%2C%22env%22%3A%7B%22screen%22%3A%222560X1440%22%2C%22tz%22%3A%22%2B9%22%2C%22cke%22%3A%22Y%22%2C%22uadata%22%3A%7B%22fullVersionList%22%3A%5B%7B%22brand%22%3A%22Not)A%3BBrand%22%2C%22version%22%3A%2299.0.0.0%22%7D%2C%7B%22brand%22%3A%22Google%20Chrome%22%2C%22version%22%3A%22127.0.6533.122%22%7D%2C%7B%22brand%22%3A%22Chromium%22%2C%22version%22%3A%22127.0.6533.122%22%7D%5D%2C%22mobile%22%3Afalse%2C%22model%22%3A%22%22%2C%22platform%22%3A%22Windows%22%2C%22platformVersion%22%3A%2219.0.0%22%7D%7D%2C%22common%22%3A%7B%22session_timeout%22%3A%221800%22%2C%22svcdomain%22%3A%22m.map.kakao.com%22%2C%22deployment%22%3A%22production%22%2C%22url%22%3A%22https%3A%2F%2Fm.map.kakao.com%2Factions%2FsearchView%3Fq%3D%25EB%25A7%259B%25EC%25A7%2591%26wxEnc%3DLWQOQP%26wyEnc%3DQNMOOTN%26lvl%3D8%26rect%3D440535%2C1083750%2C533975%2C1181030%26viewmap%3Dtrue%26BT%3D1724597156464%22%2C%22title%22%3A%22%EA%B2%80%EC%83%89%20%7C%20%EC%B9%B4%EC%B9%B4%EC%98%A4%EB%A7%B5%22%2C%22page%22%3A%22searchView%22%7D%2C%22etc%22%3A%7B%22client_info%22%3A%7B%22tuid%22%3A%22w-1b6p4TkQknRW_240825235941371%22%2C%22tsid%22%3A%22w-1b6p4TkQknRW_240825235941371%22%2C%22uuid%22%3A%22w-YLfjwpIHkv80_240825618086138%22%2C%22suid%22%3A%22w-YLfjwpIHkv80_240825618086138%22%2C%22isuid%22%3A%22w-F2EClk9h3zS3_240825650573437%22%2C%22client_timestamp%22%3A1724597981824%7D%7D%2C%22action%22%3A%7B%22type%22%3A%22Event%22%2C%22name%22%3A%22searchView%22%2C%22kind%22%3A%22%22%7D%2C%22custom_props%22%3A%7B%22te1%22%3A%22layeron%22%2C%22te2%22%3A%22M%22%7D%7D&uncri=33986&uncrt=0";
        Map<String, String> headers = new HashMap<>();
        headers.put("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");
        try (Response response = requestGetWithNoRedirect(requestUrl, headers)) {
            Map<String, List<String>> headerMap = response.headers().toMultimap();
            String cookie = headerMap.get("set-cookie").stream().map(str -> {
                int index = str.indexOf(';');
                return str.substring(0, index) + ";";
            }).collect(Collectors.joining(""));

            cookie += "__T=1;";
            cookie += "__T_SECURE=1;";

            Map<String, String> newHeaders = new HashMap<>();
            newHeaders.put("Host", "search.map.kakao.com");
            newHeaders.put("referer", "https://map.kakao.com/");
            newHeaders.put("cookie", cookie);

            return newHeaders;

        } catch (Exception e) {
            return null;
        }
    }

    public static Response requestPost(String url, String requestBody)
            throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(requestBody, mediaType);

        return request("POST", url, body);
    }

    private static Response request(String method, String url, RequestBody body)
            throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .connectTimeout(Duration.ofSeconds(120L))
                .readTimeout(Duration.ofSeconds(120L))
                .build();

        Request.Builder builder = new Request.Builder().url(url).method(method, body);
//        header.forEach(builder::addHeader);
        Request request = builder.build();

        return client.newCall(request).execute();
    }

    public static Response requestGetWithNoRedirect(String url, Map<String, String> header) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .followRedirects(false)
                .followSslRedirects(false)
                .retryOnConnectionFailure(true)
                .connectTimeout(Duration.ofSeconds(120L))
                .readTimeout(Duration.ofSeconds(120L))
                .build();

        Request.Builder builder = new Request.Builder().url(url).method("GET", null);
        header.forEach(builder::addHeader);
        Request request = builder.build();

        return client.newCall(request).execute();
    }

    public Set<String> generateSquares(int x1, int y1, int x2, int y2) {
        Set<String> coordinates = new HashSet<>();

        // 100 단위로 쪼개기
        for (int x = x1; x < x2; x += 500) {
            for (int y = y1; y < y2; y += 500) {
                coordinates.add(String.format("%d,%d,%d,%d", x, y, x + 500, y + 500));
            }
        }

        return coordinates;
    }

}
