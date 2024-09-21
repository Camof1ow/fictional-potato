package com.example.japanesenamegenerator.diner.application;

import com.example.japanesenamegenerator.diner.application.response.DinerDetailResponseDTO;
import com.example.japanesenamegenerator.diner.application.response.DinerInfoResponseDTO;
import com.example.japanesenamegenerator.diner.application.response.PlaceDetailDTO;
import com.example.japanesenamegenerator.diner.domain.*;
import com.example.japanesenamegenerator.diner.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DinerService {

    private final DinerInfoRepository dinerInfoRepository;
    private final DinerCommentRepository dinerCommentRepository;
    private final DinerDetailRepository dinerDetailRepository;
    private final DinerQueryRepository dinerQueryRepository;
    private final DinerMenuRepository dinerMenuRepository;
    private final DinerPhotoRepository dinerPhotoRepository;

    @Transactional
    public void deleteInfo(String confirmId) {
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
        Boolean isValid = coordIsValid(lon1, lon2, lat1, lat2);
        //todo : lon1 lon2 , lat1 lat2 크기 비교 후 순서대로.
        return dinerQueryRepository.findAllByCoordinate(lon1, lon2, lat1, lat2, pageable);
    }

    public DinerDetailResponseDTO getDinerDetail(Long confirmId) {
        DinerDetail byConfirmId = dinerDetailRepository.findByConfirmId(confirmId).orElseGet(()-> DinerDetail.builder().build());
        List<DinerComment> dinerComments = dinerCommentRepository.findAllByConfirmId(confirmId);
        List<DinerMenu> menuList = dinerMenuRepository.findAllByConfirmId(confirmId);
        List<DinerPhoto> photoList = dinerPhotoRepository.findAllByConfirmId(confirmId);

        return DinerDetailResponseDTO.from(byConfirmId, dinerComments, menuList,photoList);
    }

    private Boolean coordIsValid(Double lon1, Double lon2, Double lat1, Double lat2) {

        if (lat1 == null || lat2 == null || lon1 == null || lon2 == null) {
            return false;

        } else if (lat1.isNaN() || lat2.isNaN() || lon1.isNaN() || lon2.isNaN()) {
            return false;

        } else if (lat1.isInfinite() || lat2.isInfinite() || lon1.isInfinite() || lon2.isInfinite()) {
            return false;
        } else {
            return true;
        }

    }


}
