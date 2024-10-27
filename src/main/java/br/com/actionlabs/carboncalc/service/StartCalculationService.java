package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.dto.StartCalcRequestDTO;
import br.com.actionlabs.carboncalc.dto.StartCalcResponseDTO;
import br.com.actionlabs.carboncalc.model.Calculation;
import br.com.actionlabs.carboncalc.model.User;
import br.com.actionlabs.carboncalc.repository.CalculationRepository;
import br.com.actionlabs.carboncalc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartCalculationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CalculationRepository calculationRepository;

    public StartCalcResponseDTO startCalculation(StartCalcRequestDTO requestDTO) {
        
        User user = userRepository.findByEmail(requestDTO.getEmail()).orElse(null);
        if (user == null) {
            user = new User();
            user.setName(requestDTO.getName());
            user.setEmail(requestDTO.getEmail());
            user.setUf(requestDTO.getUf());
            user.setPhoneNumber(requestDTO.getPhoneNumber());
            user = userRepository.save(user); 
        }       

        Calculation calculation = new Calculation();
        calculation.setUserId(user.getId());
        calculationRepository.save(calculation);

        StartCalcResponseDTO responseDTO = new StartCalcResponseDTO();
        responseDTO.setId(calculation.getId());

        return responseDTO;
    }
}
