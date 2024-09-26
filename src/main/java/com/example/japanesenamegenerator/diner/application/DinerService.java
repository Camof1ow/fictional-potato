package com.example.japanesenamegenerator.diner.application;

import com.example.japanesenamegenerator.common.util.CoordinateUtil;
import com.example.japanesenamegenerator.diner.application.response.DinerDetailResponseDTO;
import com.example.japanesenamegenerator.diner.application.response.DinerInfoResponseDTO;
import com.example.japanesenamegenerator.diner.application.response.PlaceData;
import com.example.japanesenamegenerator.diner.application.response.PlaceDetailDTO;
import com.example.japanesenamegenerator.diner.domain.*;
import com.example.japanesenamegenerator.diner.repository.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.japanesenamegenerator.common.util.OkHttpUtil.*;

@Service
@RequiredArgsConstructor
public class DinerService {

    private final DinerInfoRepository dinerInfoRepository;
    private final DinerCommentRepository dinerCommentRepository;
    private final DinerDetailRepository dinerDetailRepository;
    private final DinerQueryRepository dinerQueryRepository;
    private final DinerMenuRepository dinerMenuRepository;
    private final DinerPhotoRepository dinerPhotoRepository;
    private final DataSource dataSource;

    private static List<DinerDetail> dinerDetails = new ArrayList<>();
    private static List<DinerMenu> dinerMenus = new ArrayList<>();
    private static List<DinerPhoto> dinerPhotos = new ArrayList<>();
    private static List<DinerComment> dinerComments = new ArrayList<>();
    private static List<DinerInfo> dinerInfos = new ArrayList<>();

    @Transactional
    public void deleteInfo(Long confirmId) {
        dinerInfoRepository.deleteByConfirmId(confirmId);
    }

    @Transactional
    public void updateCommentUsername() {
        List<Long> allIdByUsernameIsNull = dinerCommentRepository.findAllIdByUsernameIsNull();
        for (Long id : allIdByUsernameIsNull) {
            dinerCommentRepository.updateUsername(id, PlaceDetailDTO.getRandomNickName());
        }
    }


    public Page<DinerInfoResponseDTO> getDinersInArea(Double lon1, Double lon2, Double lat1, Double lat2, Pageable pageable) {
        //todo : lon1 lon2 , lat1 lat2 크기 비교 후 순서대로.

        Page<DinerInfoResponseDTO> allByCoordinate = dinerQueryRepository.findAllByCoordinate(
                Math.min(lon1, lon2), Math.max(lon1, lon2),
                Math.min(lat1, lat2), Math.max(lat1, lat2)
                , pageable);

        if (allByCoordinate.getTotalElements() == 0) {
            System.out.println("수집로직필요");
        }

        return allByCoordinate;
    }

    public DinerDetailResponseDTO getDinerDetail(Long confirmId) {
        DinerDetail byConfirmId = dinerDetailRepository.findByConfirmId(confirmId).orElseGet(() -> DinerDetail.builder().build());
        List<DinerComment> dinerComments = dinerCommentRepository.findAllByConfirmId(confirmId);
        List<DinerMenu> menuList = dinerMenuRepository.findAllByConfirmId(confirmId);
        List<DinerPhoto> photoList = dinerPhotoRepository.findAllByConfirmId(confirmId);

        return DinerDetailResponseDTO.from(byConfirmId, dinerComments, menuList, photoList);
    }

    private Set<String> generateSquares(int x1, int y1, int x2, int y2) {
        Set<String> coordinates = new HashSet<>();

        for (int x = x1; x < x2; x += 500) {
            for (int y = y1; y < y2; y += 500) {
                coordinates.add(String.format("%d,%d,%d,%d", x, y, x + 500, y + 500));
            }
        }
        return coordinates;
    }

    public void crawlDinerInfo(Double lat1, Double lon1, Double lat2, Double lon2) {
        Map<String, Integer> wCongnamul1 = CoordinateUtil.convertToWCongnamul(lat1, lon1);
        Map<String, Integer> wCongnamul2 = CoordinateUtil.convertToWCongnamul(lat2, lon2);

        Set<String> rectangleCoordinates = generateSquares(
                Math.min(wCongnamul1.get("x"), wCongnamul2.get("x")),
                Math.min(wCongnamul1.get("y"), wCongnamul2.get("y")),
                Math.max(wCongnamul1.get("x"), wCongnamul2.get("x")),
                Math.max(wCongnamul1.get("y"), wCongnamul2.get("y"))
        );

        Map<String, String> map = getHeaders();
        for (String rect : rectangleCoordinates) {
            int pageNo = 1;

            while (true) {
                String query = String.format("page=%d&rect=%s", pageNo, rect);
                String requestUrl = "https://search.map.kakao.com/mapsearch/map.daum?sort=0&callback=jQuery18106682811413432699_1725244537609&q=%EC%8B%9D%EB%8B%B9&msFlag=S&mcheck=Y&" + query;

                try (Response response = requestGetWithHeaders(requestUrl, map)) {
                    if (!response.isSuccessful() || response.request().url().pathSegments().contains("500.ko.html")) {
                        map = getHeaders();  // 헤더를 다시 가져오는 로직 유지
                        continue;
                    }

                    String responseBody = unWrapJsonString(response.body().string());
                    PlaceData placeDto = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                            .readValue(responseBody, PlaceData.class);

                    if (placeDto.getPlace_totalcount() == 0) break;  // 불러올 데이터가 없으면 루프 종료

                    List<DinerInfo> filteredList = placeDto.getPlace().stream()
                            .map(DinerInfo::new)
                            .filter(dinerInfo -> !dinerInfoRepository.existsByConfirmId((long) dinerInfo.getConfirmId()))
                            .collect(
                                    Collectors.toMap(
                                            DinerInfo::getConfirmId,
                                            dinerInfo -> dinerInfo,
                                            (existing, replacement) -> replacement
                                    )
                            ).values().stream().toList();

                    dinerInfos.addAll(filteredList);

                    if (dinerInfos.size() >= 500) {
                        dinerQueryRepository.upsertDinerInfos(dinerInfos);
                        dinerInfos.clear();
                    }
                    pageNo++;

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
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

    public void insertDetail(PlaceDetailDTO placeDetailDTO) {
        DinerDetail detail = placeDetailDTO.toDetailEntity();
        List<DinerComment> dinerCommentList = placeDetailDTO.toDinerComments();
        List<DinerMenu> menuEntities = placeDetailDTO.toMenuEntities();
        List<DinerPhoto> photoEntities = placeDetailDTO.toPhotoEntities();

        if (detail == null) {
            return;
        }

        dinerDetails.add(detail);
        dinerMenus.addAll(menuEntities);
        dinerPhotos.addAll(photoEntities);
        dinerComments.addAll(dinerCommentList);

        if (dinerDetails.size() >= 500) {
            dinerDetailRepository.saveAll(dinerDetails);
            dinerDetails.clear();

            dinerMenuRepository.saveAll(dinerMenus);
            dinerMenus.clear();

            dinerPhotoRepository.saveAll(dinerPhotos);
            dinerPhotos.clear();

            dinerCommentRepository.saveAll(dinerComments);
            dinerComments.clear();
        }

    }

    public void crawlDetail() {
        List<Long> allConfirmIdsWithoutDetail = dinerInfoRepository.findAllConfirmIdsWithoutDetail();

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        for (Long id : allConfirmIdsWithoutDetail) {
            if (!dinerDetailRepository.existsByConfirmId(id)) {
                String url = "https://place.map.kakao.com/main/v/" + id;
                try (Response response = requestGetWithHeaders(url, null)) {
                    String string = response.body().string();
                    PlaceDetailDTO placeDetailDTO = objectMapper.readValue(string, PlaceDetailDTO.class);
                    insertDetail(placeDetailDTO);

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        if (dinerDetails != null && !dinerDetails.isEmpty()) {
            dinerDetailRepository.saveAll(dinerDetails);
            dinerDetails.clear();

            dinerMenuRepository.saveAll(dinerMenus);
            dinerMenus.clear();

            dinerPhotoRepository.saveAll(dinerPhotos);
            dinerPhotos.clear();

            dinerCommentRepository.saveAll(dinerComments);
            dinerComments.clear();
        }

    }


}
