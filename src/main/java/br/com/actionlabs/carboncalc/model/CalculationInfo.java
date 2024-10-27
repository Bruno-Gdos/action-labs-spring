package br.com.actionlabs.carboncalc.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import br.com.actionlabs.carboncalc.dto.TransportationDTO;
import br.com.actionlabs.carboncalc.dto.UpdateCalculationInfoRequestDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("calculationInfo")
public class CalculationInfo {
    @Id
    private String id;
    private String userId; 
    private int energyConsumption;
    private List<TransportationDTO> transportation;
    private int solidWasteTotal;
    private double recyclePercentage;

    public CalculationInfo(String id, String userId, UpdateCalculationInfoRequestDTO calculationInfoDto) {
      this.id = id;
      this.energyConsumption = calculationInfoDto.getEnergyConsumption();
      this.transportation = calculationInfoDto.getTransportation();
      this.solidWasteTotal = calculationInfoDto.getSolidWasteTotal();
      this.recyclePercentage = calculationInfoDto.getRecyclePercentage();
      this.userId = userId;
    }
    
    public CalculationInfo(String userId){
      this.userId = userId;
    }
}
