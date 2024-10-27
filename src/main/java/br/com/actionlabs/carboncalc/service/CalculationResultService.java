package br.com.actionlabs.carboncalc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionlabs.carboncalc.dto.CarbonCalculationResultDTO;
import br.com.actionlabs.carboncalc.dto.TransportationDTO;
import br.com.actionlabs.carboncalc.enums.TransportationType;
import br.com.actionlabs.carboncalc.model.CalculationInfo;
import br.com.actionlabs.carboncalc.model.SolidWasteEmissionFactor;
import br.com.actionlabs.carboncalc.model.TransportationEmissionFactor;
import br.com.actionlabs.carboncalc.model.User;
import br.com.actionlabs.carboncalc.repository.CalculationInfoRepository;
import br.com.actionlabs.carboncalc.repository.EnergyEmissionFactorRepository;
import br.com.actionlabs.carboncalc.repository.SolidWasteEmissionFactorRepository;
import br.com.actionlabs.carboncalc.repository.TransportationEmissionFactorRepository;
import br.com.actionlabs.carboncalc.repository.UserRepository;

import java.util.List;


@Service
public class CalculationResultService {

    @Autowired
    private EnergyEmissionFactorRepository energyEmissionFactorRepository;

    @Autowired
    private CalculationInfoRepository calculationInfoRepository;

    @Autowired
    private SolidWasteEmissionFactorRepository solidWasteEmissionFactorRepository;

    @Autowired
    private TransportationEmissionFactorRepository transportationEmissionFactorRepository;

    @Autowired
    private UserRepository userRepository;

    public CarbonCalculationResultDTO calculateCarbonEmission(String calculationId) {

        CalculationInfo calculationInfo = calculationInfoRepository.findById(calculationId).orElse(null);
        if (calculationInfo == null) {
            throw new RuntimeException("Calculation not found");
        }
        User user = userRepository.findById(calculationInfo.getUserId()).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        
        String calculationUf = user.getUf();
        double energyConsumption = calculationInfo.getEnergyConsumption();
        double recyclePercentage = calculationInfo.getRecyclePercentage();
        int solidWasteTotal = calculationInfo.getSolidWasteTotal();
        List<TransportationDTO> transportations = calculationInfo.getTransportation();

        double energyEmissionFactor = energyEmissionFactorRepository.findById(calculationUf).orElse(null).getFactor();
        SolidWasteEmissionFactor solidWasteEmissionFactor = solidWasteEmissionFactorRepository.findById(calculationUf).orElse(null);

        double recyclableEmissionFactor = solidWasteEmissionFactor.getRecyclableFactor();
        double nonRecyclableEmissionFactor = solidWasteEmissionFactor.getNonRecyclableFactor();

        double energyEmission = calculateEnergyEmission(energyEmissionFactor, energyConsumption);
        double transportationEmission = calculateTransportationEmission(transportations);
        double solidWasteEmission = calculateSolidWasteEmission(solidWasteTotal, recyclePercentage, recyclableEmissionFactor, nonRecyclableEmissionFactor);

        double totalEmission = energyEmission + transportationEmission + solidWasteEmission;

        CarbonCalculationResultDTO resultDTO = new CarbonCalculationResultDTO(energyEmission, transportationEmission, solidWasteEmission, totalEmission);
        return resultDTO;

    }

    private double calculateEnergyEmission(double emissionFactor, double energyConsumption) {
        
        return emissionFactor * energyConsumption;

    }

    private double calculateTransportationEmission( List<TransportationDTO> transportations) {
        
        double totalEmission = 0;
        for (TransportationDTO transportation : transportations) {
            double distance = transportation.getMonthlyDistance();
            TransportationType type = transportation.getType();
            TransportationEmissionFactor transportationEmissionFactor = transportationEmissionFactorRepository.findById(type).orElse(null);
            if (transportationEmissionFactor == null) {
                throw new RuntimeException("Transportation emission factor not found");
            }
            double emissionFactor = transportationEmissionFactor.getFactor();
            totalEmission += distance * emissionFactor;
        }
        return totalEmission;
        
    }

    private double calculateSolidWasteEmission(int solidWasteTotal, double recyclePercentage, double recyclableEmissionFactor, double nonRecyclableEmissionFactor) {
        double recyclableEmission = solidWasteTotal * recyclePercentage * recyclableEmissionFactor;
        double nonRecyclableEmission = solidWasteTotal * (1 - recyclePercentage) * nonRecyclableEmissionFactor;
        return recyclableEmission + nonRecyclableEmission;
    }
}
