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
        //todo : lon1 lon2 , lat1 lat2 크기 비교 후 순서대로.

        Page<DinerInfoResponseDTO> allByCoordinate = dinerQueryRepository.findAllByCoordinate(
                Math.min(lon1, lon2), Math.max(lon1, lon2),
                Math.min(lat1, lat2), Math.max(lat1, lat2)
                , pageable);

        if(allByCoordinate.getTotalElements() == 0){
            System.out.println("수집로직필요");
        }

        return allByCoordinate;
    }

    public DinerDetailResponseDTO getDinerDetail(Long confirmId) {
        DinerDetail byConfirmId = dinerDetailRepository.findByConfirmId(confirmId).orElseGet(()-> DinerDetail.builder().build());
        List<DinerComment> dinerComments = dinerCommentRepository.findAllByConfirmId(confirmId);
        List<DinerMenu> menuList = dinerMenuRepository.findAllByConfirmId(confirmId);
        List<DinerPhoto> photoList = dinerPhotoRepository.findAllByConfirmId(confirmId);

        return DinerDetailResponseDTO.from(byConfirmId, dinerComments, menuList,photoList);
    }

}
