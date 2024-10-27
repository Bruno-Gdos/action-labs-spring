package br.com.actionlabs.carboncalc.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import br.com.actionlabs.carboncalc.dto.TransportationDTO;

@Data
@Document("calculationInfo")
public class CalculationInfo {
      @Id
    private String id;
    private String CalculationId; 
    private int energyConsumption;
    private List<TransportationDTO> transportation;
    private int solidWasteTotal;
    private double recyclePercentage;
}
