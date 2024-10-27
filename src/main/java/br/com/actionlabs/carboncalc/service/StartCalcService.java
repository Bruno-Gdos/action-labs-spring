package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.dto.StartCalcRequestDTO;
import br.com.actionlabs.carboncalc.dto.StartCalcResponseDTO;
import br.com.actionlabs.carboncalc.model.User;
import br.com.actionlabs.carboncalc.repository.UserRepository;
import br.com.actionlabs.carboncalc.utils.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartCalcService {

    @Autowired
    private UserRepository userRepository;

    public StartCalcResponseDTO startCalculation(StartCalcRequestDTO requestDTO) {

        try {
            Validator.validateUF(requestDTO.getUf());
            Validator.validatePhoneNumber(requestDTO.getPhoneNumber());
        } catch (IllegalArgumentException e) {
            throw e; 
        }

        User user = new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setUf(requestDTO.getUf());
        user.setPhoneNumber(requestDTO.getPhoneNumber());

        user = userRepository.save(user);

        StartCalcResponseDTO responseDTO = new StartCalcResponseDTO();
        responseDTO.setId(user.getId());

        return responseDTO;
    }
}
