package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.dto.UpdateCalcInfoResponseDTO;
import br.com.actionlabs.carboncalc.dto.UpdateCalculationInfoRequestDTO;
import br.com.actionlabs.carboncalc.model.CalculationInfo;
import br.com.actionlabs.carboncalc.repository.CalculationInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculationInfoService {

    @Autowired
    private CalculationInfoRepository calculationInfoRepository;

    public UpdateCalcInfoResponseDTO updateCalcInfo(UpdateCalculationInfoRequestDTO requestDTO) throws IllegalArgumentException {

        CalculationInfo calculationInfo = calculationInfoRepository.findById(requestDTO.getId()).orElse(null);
        if (calculationInfo == null) {
            throw new IllegalArgumentException("Calculation not found");
        }
        CalculationInfo updatedCalculationInfo = new CalculationInfo(requestDTO.getId(), calculationInfo.getUserId(), requestDTO);
        calculationInfoRepository.save(updatedCalculationInfo);

        UpdateCalcInfoResponseDTO responseDTO = new UpdateCalcInfoResponseDTO();
        responseDTO.setSuccess(true);

        return responseDTO;
    }
}
