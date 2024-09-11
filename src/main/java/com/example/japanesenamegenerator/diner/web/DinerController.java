package com.example.japanesenamegenerator.diner.web;

import com.example.japanesenamegenerator.diner.application.DinerService;
import com.example.japanesenamegenerator.diner.application.response.DinerDetailResponseDTO;
import com.example.japanesenamegenerator.diner.application.response.DinerInfoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diner")
public class DinerController {

    private final DinerService dinerService;

    @Operation(summary = "Get diners with coordinates", description = "Fetch diners within specified coordinates.")
    @GetMapping("/coord")
    public List<DinerInfoResponseDTO> getDinerWithCoordinate(@RequestParam Double lon1, @RequestParam Double lon2,
                                          @RequestParam Double lat1,@RequestParam Double lat2) {

        return dinerService.getDinersInArea(lon1, lon2, lat1, lat2);
    }

    @Operation(summary = "Get diner detail", description = "Fetch detailed information for a specific diner.")
    @GetMapping("/detail")
    public DinerDetailResponseDTO getDinerDetail(@RequestParam Long confirmId) {
        return dinerService.getDinerDetail(confirmId);
    }



}
