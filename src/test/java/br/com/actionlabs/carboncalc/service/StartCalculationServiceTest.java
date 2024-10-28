package br.com.actionlabs.carboncalc.service;


import br.com.actionlabs.carboncalc.dto.StartCalcRequestDTO;
import br.com.actionlabs.carboncalc.dto.StartCalcResponseDTO;
import br.com.actionlabs.carboncalc.model.CalculationInfo;
import br.com.actionlabs.carboncalc.model.User;
import br.com.actionlabs.carboncalc.repository.CalculationInfoRepository;
import br.com.actionlabs.carboncalc.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class StartCalculationServiceTest {

    @InjectMocks
    private StartCalculationService startCalculationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CalculationInfoRepository calculationInfoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStartCalculation_NewUser() {
        StartCalcRequestDTO requestDTO = new StartCalcRequestDTO();
        requestDTO.setName("John Doe");
        requestDTO.setEmail("john.doe@example.com");
        requestDTO.setUf("sp");
        requestDTO.setPhoneNumber("123456789");

        when(userRepository.findByEmail(requestDTO.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(calculationInfoRepository.save(any(CalculationInfo.class))).thenAnswer(invocation -> {
            CalculationInfo calculationInfo = invocation.getArgument(0);
            calculationInfo.setId("TestId");
            return calculationInfo;
        });

        StartCalcResponseDTO responseDTO = startCalculationService.startCalculation(requestDTO);

        assertEquals("TestId", responseDTO.getId());
        verify(userRepository, times(1)).save(any(User.class));
        verify(calculationInfoRepository, times(1)).save(any(CalculationInfo.class));
    }

    @Test
    public void testStartCalculation_ExistingUser() {
        StartCalcRequestDTO requestDTO = new StartCalcRequestDTO();
        requestDTO.setName("John Doe");
        requestDTO.setEmail("john.doe@example.com");
        requestDTO.setUf("sp");
        requestDTO.setPhoneNumber("123456789");

        User existingUser = new User();
        existingUser.setId("TestId");
        existingUser.setName("John Doe");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setUf("SP");
        existingUser.setPhoneNumber("123456789");

        when(userRepository.findByEmail(requestDTO.getEmail())).thenReturn(Optional.of(existingUser));
        when(calculationInfoRepository.save(any(CalculationInfo.class))).thenAnswer(invocation -> {
            CalculationInfo calculationInfo = invocation.getArgument(0);
            calculationInfo.setId("TestId");
            return calculationInfo;
        });

        StartCalcResponseDTO responseDTO = startCalculationService.startCalculation(requestDTO);

        assertEquals("TestId", responseDTO.getId());
        verify(userRepository, never()).save(any(User.class));
        verify(calculationInfoRepository, times(1)).save(any(CalculationInfo.class));
    }
}