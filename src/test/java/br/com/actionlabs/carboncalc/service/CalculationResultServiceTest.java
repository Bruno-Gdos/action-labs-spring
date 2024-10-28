package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.dto.CarbonCalculationResultDTO;
import br.com.actionlabs.carboncalc.dto.TransportationDTO;
import br.com.actionlabs.carboncalc.enums.TransportationType;
import br.com.actionlabs.carboncalc.model.CalculationInfo;
import br.com.actionlabs.carboncalc.model.EnergyEmissionFactor;
import br.com.actionlabs.carboncalc.model.SolidWasteEmissionFactor;
import br.com.actionlabs.carboncalc.model.TransportationEmissionFactor;
import br.com.actionlabs.carboncalc.model.User;
import br.com.actionlabs.carboncalc.repository.CalculationInfoRepository;
import br.com.actionlabs.carboncalc.repository.EnergyEmissionFactorRepository;
import br.com.actionlabs.carboncalc.repository.SolidWasteEmissionFactorRepository;
import br.com.actionlabs.carboncalc.repository.TransportationEmissionFactorRepository;
import br.com.actionlabs.carboncalc.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalculationResultServiceTest {

    @Mock
    private EnergyEmissionFactorRepository energyEmissionFactorRepository;

    @Mock
    private CalculationInfoRepository calculationInfoRepository;

    @Mock
    private SolidWasteEmissionFactorRepository solidWasteEmissionFactorRepository;

    @Mock
    private TransportationEmissionFactorRepository transportationEmissionFactorRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CalculationResultService calculationResultService;

    private CalculationInfo calculationInfo;
    private User user;
    private SolidWasteEmissionFactor solidWasteEmissionFactor;
    private TransportationEmissionFactor transportationEmissionFactor;

    @BeforeEach
    public void setUp() {
        calculationInfo = new CalculationInfo();
        calculationInfo.setId("calcId");
        calculationInfo.setUserId("userId");
        calculationInfo.setEnergyConsumption(100);
        calculationInfo.setRecyclePercentage(0.5);
        calculationInfo.setSolidWasteTotal(200);
        calculationInfo.setTransportation(Arrays.asList(new TransportationDTO(TransportationType.CAR, 100)));

        user = new User();
        user.setId("userId");
        user.setUf("SP");

        solidWasteEmissionFactor = new SolidWasteEmissionFactor();
        solidWasteEmissionFactor.setRecyclableFactor(0.1);
        solidWasteEmissionFactor.setNonRecyclableFactor(0.2);

        transportationEmissionFactor = new TransportationEmissionFactor();
        transportationEmissionFactor.setFactor(0.3);
    }

    @Test
    public void testCalculateCarbonEmission() {
        when(calculationInfoRepository.findById("calcId")).thenReturn(Optional.of(calculationInfo));
        when(userRepository.findById("userId")).thenReturn(Optional.of(user));
        EnergyEmissionFactor energyEmissionFactor = new EnergyEmissionFactor();
        energyEmissionFactor.setFactor(0.4);
        when(energyEmissionFactorRepository.findById("SP")).thenReturn(Optional.of(energyEmissionFactor));
        when(solidWasteEmissionFactorRepository.findById("SP")).thenReturn(Optional.of(solidWasteEmissionFactor));
        when(transportationEmissionFactorRepository.findById(TransportationType.CAR)).thenReturn(Optional.of(transportationEmissionFactor));

        CarbonCalculationResultDTO result = calculationResultService.calculateCarbonEmission("calcId");

        assertEquals(40, result.getEnergy());
        assertEquals(30, result.getTransportation());
        assertEquals(30, result.getSolidWaste());
        assertEquals(100, result.getTotal());
    }

    @Test
    public void testCalculateCarbonEmission_CalculationNotFound() {
        when(calculationInfoRepository.findById("calcId")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            calculationResultService.calculateCarbonEmission("calcId");
        });

        assertEquals("Calculation not found", exception.getMessage());
    }

    @Test
    public void testCalculateCarbonEmission_UserNotFound() {
        when(calculationInfoRepository.findById("calcId")).thenReturn(Optional.of(calculationInfo));
        when(userRepository.findById("userId")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            calculationResultService.calculateCarbonEmission("calcId");
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testCalculateCarbonEmission_TransportationEmissionFactorNotFound() {
        when(calculationInfoRepository.findById("calcId")).thenReturn(Optional.of(calculationInfo));
        when(userRepository.findById("userId")).thenReturn(Optional.of(user));
        EnergyEmissionFactor energyEmissionFactor = new EnergyEmissionFactor();
        energyEmissionFactor.setFactor(0.4);
        when(energyEmissionFactorRepository.findById("SP")).thenReturn(Optional.of(energyEmissionFactor));
        when(solidWasteEmissionFactorRepository.findById("SP")).thenReturn(Optional.of(solidWasteEmissionFactor));
        when(transportationEmissionFactorRepository.findById(TransportationType.CAR)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            calculationResultService.calculateCarbonEmission("calcId");
        });

        assertEquals("Transportation emission factor not found", exception.getMessage());
    }
}