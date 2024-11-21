package com.example.japanesenamegenerator;

import com.example.japanesenamegenerator.common.util.CoordinateUtil;
import com.example.japanesenamegenerator.diner.application.DinerService;
import com.example.japanesenamegenerator.diner.domain.*;
import com.example.japanesenamegenerator.diner.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;

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
// 위도(Latitude) : 37.53860806879741 / 경도(Longitude) : 126.95827843632695
// 위도(Latitude) : 37.53020866625238 / 경도(Longitude) : 126.97415120981688
        Double lat1 = 37.53860806879741;
        Double lon1 = 126.95827843632695;
        Double lat2 = 37.53020866625238;
        Double lon2 = 126.97415120981688;
        dinerService.crawlDinerInfo(lat1, lon1, lat2, lon2);

    }

    @Test
    void crawlDinerDetailTest() {
        dinerService.crawlDetail();
    }
//
//
//    @Test
//    void coordinateConvertTest() {
//
//        DinerInfo byId = dinerInfoRepository.findById(1L).orElse(null);
//        Map<String, Integer> wCongnamul = CoordinateUtil.convertToWCongnamul(byId.getLat(), byId.getLon());
//        Map<String, Double> epsg5181 = CoordinateUtil.convertToWGS84(byId.getX(), byId.getY());
//        System.out.println();
//    }


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
