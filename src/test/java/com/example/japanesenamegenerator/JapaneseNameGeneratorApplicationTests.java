package com.example.japanesenamegenerator;

import com.example.japanesenamegenerator.common.util.CoordinateUtil;
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


    @Test
    void crawlDinerInfoTest() throws IOException {
//        //위도(Latitude) : 37.54185142033361 / 경도(Longitude) : 127.06312737569982
//        // 위도(Latitude) : 37.48105289593571 / 경도(Longitude) : 127.15994439230138
//        Double lat1 = 37.54185142033361;
//        Double lon1 = 127.06312737569982;
//        Double lat2 = 37.48105289593571;
//        Double lon2 = 127.15994439230138;
//        dinerService.crawlDinerInfo(lat1, lon1, lat2, lon2);
        dinerService.crawlDetail();

    }


    @Test
    void coordinateConvertTest() {

        DinerInfo byId = dinerInfoRepository.findById(1L).orElse(null);
        Map<String, Integer> wCongnamul = CoordinateUtil.convertToWCongnamul(byId.getLat(), byId.getLon());
        Map<String, Double> epsg5181 = CoordinateUtil.convertToWGS84(byId.getX(), byId.getY());
        System.out.println();
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


    public Set<String> generateSquares(int x1, int y1, int x2, int y2) {
        Set<String> coordinates = new HashSet<>();

        for (int x = x1; x < x2; x += 500) {
            for (int y = y1; y < y2; y += 500) {
                coordinates.add(String.format("%d,%d,%d,%d", x, y, x + 500, y + 500));
            }
        }
        return coordinates;
    }


}
