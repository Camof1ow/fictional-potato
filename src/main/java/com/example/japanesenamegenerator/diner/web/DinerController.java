package com.example.japanesenamegenerator.diner.web;

import com.example.japanesenamegenerator.diner.application.DinerService;
import com.example.japanesenamegenerator.diner.application.response.DinerDetailResponseDTO;
import com.example.japanesenamegenerator.diner.application.response.DinerInfoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diner")
public class DinerController {

    private final DinerService dinerService;

    @Operation(summary = "지정범위 내 식당 가져오기",
            description = "위도 경도를 통한 좌표를 통해 식당들의 정보를 가져옵니다.",
            parameters = {
                    @Parameter(
                            name = "lon1",
                            description = "좌표 좌상단의 longitude",
                            example = "126.99"
                    ),
                    @Parameter(
                            name = "lon2",
                            description = "좌표 우하단의 longitude",
                            example = "126.99885"
                    ),
                    @Parameter(
                            name = "lat1",
                            description = "좌표 좌상단의 latitude",
                            example = "37.533"
                    ),
                    @Parameter(
                            name = "lat2",
                            description = "좌표 우하단의 latitude",
                            example = "37.539"
                    ),
                    @Parameter(
                            name = "page",
                            description = "조회할 페이지 번호 (0부터 시작)",
                            example = "0"
                    ),
                    @Parameter(
                            name = "size",
                            description = "한 페이지에 포함될 데이터 개수",
                            example = "10"
                    )
            }
    )
    @GetMapping("/coordinate")
    public Page<DinerInfoResponseDTO> getDinerWithCoordinate(@RequestParam @Validated Double lon1,
                                                             @RequestParam @Validated Double lon2,
                                                             @RequestParam @Validated Double lat1,
                                                             @RequestParam @Validated Double lat2,
                                                             @Parameter(hidden = true) Pageable pageable) {

        return dinerService.getDinersInArea(lon1, lon2, lat1, lat2, pageable);
    }

    @Operation(summary = "식당 디테일 정보 조회",
            description = "confirmId로 식당의 세부정보를 가져옵니다",
            parameters = {@Parameter(
                    name = "confirmId",
                    description = "식당의 confirmId",
                    example = "1715457845"
            )}
    )
    @GetMapping("/detail/{confirm-id}")
    public DinerDetailResponseDTO getDinerDetail(@PathVariable(name="confirm-id") @Validated Long confirmId) {
        return dinerService.getDinerDetail(confirmId);
    }

    @Operation(summary = "10개 마커 램덤 생성",
            description = "해당 지역 내 무작위 10곳 식당의 마커를 가져옵니다."

    )
    @GetMapping("/marker")
    public List<Map<String, Double>> get10MarkersFromPlace(@RequestParam @Validated Double lon1,
                                                           @RequestParam @Validated Double lon2,
                                                           @RequestParam @Validated Double lat1,
                                                           @RequestParam @Validated Double lat2){
        return dinerService.get10MarkersFromPlace(lon1, lon2, lat1, lat2);
    }


}
