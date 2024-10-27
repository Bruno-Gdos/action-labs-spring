package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.dto.UpdateCalcInfoRequestDTO;
import br.com.actionlabs.carboncalc.dto.UpdateCalcInfoResponseDTO;
import br.com.actionlabs.carboncalc.model.CalculationInfo;
import br.com.actionlabs.carboncalc.repository.CalculationInfoRepository;
import br.com.actionlabs.carboncalc.utils.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculationInfoService {

    @Autowired
    private CalculationInfoRepository calcInfoRepository;

    public UpdateCalcInfoResponseDTO updateCalcInfo(UpdateCalcInfoRequestDTO requestDTO) throws IllegalArgumentException {
        
        Validator.validateRecyclePercentage(requestDTO.getRecyclePercentage());

        
        CalculationInfo calcInfo = new CalculationInfo();
        calcInfo.setCalculationId(requestDTO.getId());
        calcInfo.setEnergyConsumption(requestDTO.getEnergyConsumption());
        calcInfo.setTransportation(requestDTO.getTransportation());
        calcInfo.setSolidWasteTotal(requestDTO.getSolidWasteTotal());
        calcInfo.setRecyclePercentage(requestDTO.getRecyclePercentage());

        calcInfoRepository.save(calcInfo);

        UpdateCalcInfoResponseDTO responseDTO = new UpdateCalcInfoResponseDTO();
        responseDTO.setSuccess(true);

        return responseDTO;
    }
}
