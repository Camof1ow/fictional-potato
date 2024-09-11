package com.example.japanesenamegenerator.diner.application;

import com.example.japanesenamegenerator.diner.application.response.DinerDetailResponseDTO;
import com.example.japanesenamegenerator.diner.application.response.DinerInfoResponseDTO;
import com.example.japanesenamegenerator.diner.application.response.PlaceDetailDTO;
import com.example.japanesenamegenerator.diner.domain.DinerComment;
import com.example.japanesenamegenerator.diner.domain.DinerDetail;
import com.example.japanesenamegenerator.diner.domain.DinerInfo;
import com.example.japanesenamegenerator.diner.repository.DinerCommentRepository;
import com.example.japanesenamegenerator.diner.repository.DinerDetailRepository;
import com.example.japanesenamegenerator.diner.repository.DinerInfoRepository;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public void deleteInfo(String confirmId) {
        dinerInfoRepository.deleteByConfirmId(confirmId);
    }

    @Transactional
    public void updateCommentUsername (){
        List<Long> allIdByUsernameIsNull = dinerCommentRepository.findAllIdByUsernameIsNull();
        for (Long id : allIdByUsernameIsNull) {
            dinerCommentRepository.updateUsername(id, PlaceDetailDTO.getRandomNickName());
        }
    }

    public List<DinerInfoResponseDTO> getDinersInArea(Double lon1, Double lon2, Double lat1, Double lat2) {
        Boolean isValid = coordIsValid(lon1, lon2, lat1, lat2);
        if (!isValid) {
            return null;
        }
        List<DinerInfo> allByCoordinate = dinerInfoRepository.findAllByCoordinate(lon1, lon2, lat1, lat2);
        return allByCoordinate.stream().map(DinerInfoResponseDTO::from).toList();
    }

    public DinerDetailResponseDTO getDinerDetail(Long confirmId){
        if(confirmId == null) return null;

        Optional<DinerDetail> byConfirmId = dinerDetailRepository.findByConfirmId(confirmId);
        List<DinerComment> dinerComments = dinerCommentRepository.findAllByConfirmId(confirmId);
        if(byConfirmId.isPresent()){
            DinerDetail detail = byConfirmId.get();
            return DinerDetailResponseDTO.from(detail, dinerComments);

        }else return null;
    }

    private Boolean coordIsValid(Double lon1, Double lon2, Double lat1, Double lat2) {

        if(lat1 == null || lat2 == null || lon1 == null || lon2 == null){
            return false;

        } else if(lat1.isNaN() || lat2.isNaN() || lon1.isNaN() || lon2.isNaN()){
            return false;

        } else if (lat1.isInfinite() || lat2.isInfinite() || lon1.isInfinite() || lon2.isInfinite()){
            return false;
        } else {
            return true;
        }

    }


}
