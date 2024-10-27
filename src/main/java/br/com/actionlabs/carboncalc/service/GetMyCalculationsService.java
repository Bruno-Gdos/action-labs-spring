package br.com.actionlabs.carboncalc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionlabs.carboncalc.dto.GetMyCalculationsResultDTO;
import br.com.actionlabs.carboncalc.model.CalculationInfo;
import br.com.actionlabs.carboncalc.model.User;
import br.com.actionlabs.carboncalc.repository.CalculationInfoRepository;
import br.com.actionlabs.carboncalc.repository.UserRepository;
import java.util.List;

@Service
public class GetMyCalculationsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CalculationInfoRepository calculationInfoRepository;

    public GetMyCalculationsResultDTO getMyCalculations(String email) {
        
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }       

        GetMyCalculationsResultDTO resultDTO = new GetMyCalculationsResultDTO();
        resultDTO.setUserEmail(user.getEmail());
        List<CalculationInfo> calculationsInfo = calculationInfoRepository.findByUserId(user.getId());
        if (calculationsInfo == null) {
            return resultDTO;
        }
        resultDTO.setCalculations(calculationsInfo);
        return resultDTO;
    }
    
    
}
