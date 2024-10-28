package br.com.actionlabs.carboncalc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.com.actionlabs.carboncalc.dto.GetMyCalculationsResultDTO;
import br.com.actionlabs.carboncalc.model.CalculationInfo;
import br.com.actionlabs.carboncalc.model.User;
import br.com.actionlabs.carboncalc.repository.CalculationInfoRepository;
import br.com.actionlabs.carboncalc.repository.UserRepository;

public class GetMyCalculationsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CalculationInfoRepository calculationInfoRepository;

    @InjectMocks
    private GetMyCalculationsService getMyCalculationsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMyCalculations_UserNotFound() {
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            getMyCalculationsService.getMyCalculations(email);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testGetMyCalculations_NoCalculations() {
        String email = "test@example.com";
        User user = new User();
        user.setId("TestId");
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(calculationInfoRepository.findByUserId(user.getId())).thenReturn(Collections.emptyList());

        GetMyCalculationsResultDTO result = getMyCalculationsService.getMyCalculations(email);

        assertEquals(email, result.getUserEmail());
        assertEquals(0, result.getCalculations().size());
    }

    @Test
    public void testGetMyCalculations_WithCalculations() {
        String email = "test@example.com";
        User user = new User();
        user.setId("TestId");
        user.setEmail(email);

        CalculationInfo calculationInfo = new CalculationInfo();
        calculationInfo.setId("TestId");
        calculationInfo.setUserId(user.getId());

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(calculationInfoRepository.findByUserId(user.getId())).thenReturn(Collections.singletonList(calculationInfo));

        GetMyCalculationsResultDTO result = getMyCalculationsService.getMyCalculations(email);

        assertEquals(email, result.getUserEmail());
        assertEquals(1, result.getCalculations().size());
        assertEquals(calculationInfo, result.getCalculations().get(0));
    }
}