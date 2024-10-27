package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.dto.StartCalcRequestDTO;
import br.com.actionlabs.carboncalc.dto.StartCalcResponseDTO;
import br.com.actionlabs.carboncalc.model.CalculationInfo;
import br.com.actionlabs.carboncalc.model.User;
import br.com.actionlabs.carboncalc.repository.CalculationInfoRepository;
import br.com.actionlabs.carboncalc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartCalculationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CalculationInfoRepository calculationInfoRepository;

    public StartCalcResponseDTO startCalculation(StartCalcRequestDTO requestDTO) {
        
        User user = userRepository.findByEmail(requestDTO.getEmail()).orElse(null);
        if (user == null) {
            user = new User();
            user.setName(requestDTO.getName());
            user.setEmail(requestDTO.getEmail());
            user.setUf(requestDTO.getUf().toUpperCase());
            user.setPhoneNumber(requestDTO.getPhoneNumber());
            user = userRepository.save(user); 
        }       

        CalculationInfo calculationInfo = new CalculationInfo(user.getId());
        calculationInfoRepository.save(calculationInfo);

        StartCalcResponseDTO responseDTO = new StartCalcResponseDTO();
        responseDTO.setId(calculationInfo.getId());

        return responseDTO;
    }
}
