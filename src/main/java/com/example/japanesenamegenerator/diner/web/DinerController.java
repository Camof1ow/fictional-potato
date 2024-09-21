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
                            description = "범위의 최하단 경도",
                            example = "126.99"
                    ),
                    @Parameter(
                            name = "lon2",
                            description = "범위의 최상단 경도",
                            example = "126.99885"
                    ),
                    @Parameter(
                            name = "lat1",
                            description = "범위의 최좌측 위도",
                            example = "37.533"
                    ),
                    @Parameter(
                            name = "lat2",
                            description = "범위의 최우측 위도",
                            example = "37.539"
                    ),
                    @Parameter(
                            name = "page",
                            description = "조회 할 페이지",
                            example = "0"
                    ),
                    @Parameter(
                            name = "size",
                            description = "페이지 사이즈",
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


}
