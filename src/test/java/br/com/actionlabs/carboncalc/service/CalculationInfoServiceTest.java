package br.com.actionlabs.carboncalc.service;


import br.com.actionlabs.carboncalc.dto.UpdateCalcInfoResponseDTO;
import br.com.actionlabs.carboncalc.dto.UpdateCalculationInfoRequestDTO;
import br.com.actionlabs.carboncalc.model.CalculationInfo;
import br.com.actionlabs.carboncalc.repository.CalculationInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CalculationInfoServiceTest {

    @Mock
    private CalculationInfoRepository calculationInfoRepository;

    @InjectMocks
    private CalculationInfoService calculationInfoService;

    private UpdateCalculationInfoRequestDTO requestDTO;
    private CalculationInfo calculationInfo;

    @BeforeEach
    public void setUp() {
        requestDTO = new UpdateCalculationInfoRequestDTO();
        requestDTO.setId("TestId");
        // Set other fields of requestDTO as needed

        calculationInfo = new CalculationInfo();
        calculationInfo.setId("TestId");
        calculationInfo.setUserId("TestId");
        // Set other fields of calculationInfo as needed
    }

    @Test
    public void testUpdateCalcInfo_Success() {
        when(calculationInfoRepository.findById(requestDTO.getId())).thenReturn(Optional.of(calculationInfo));
        when(calculationInfoRepository.save(any(CalculationInfo.class))).thenReturn(calculationInfo);

        UpdateCalcInfoResponseDTO responseDTO = calculationInfoService.updateCalcInfo(requestDTO);

        assertNotNull(responseDTO);
        assertTrue(responseDTO.isSuccess());
        verify(calculationInfoRepository, times(1)).findById(requestDTO.getId());
        verify(calculationInfoRepository, times(1)).save(any(CalculationInfo.class));
    }

    @Test
    public void testUpdateCalcInfo_CalculationNotFound() {
        when(calculationInfoRepository.findById(requestDTO.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculationInfoService.updateCalcInfo(requestDTO);
        });

        assertEquals("Calculation not found", exception.getMessage());
        verify(calculationInfoRepository, times(1)).findById(requestDTO.getId());
        verify(calculationInfoRepository, never()).save(any(CalculationInfo.class));
    }
}