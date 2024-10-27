package br.com.actionlabs.carboncalc.dto;

import lombok.Data;
import java.util.List;

import br.com.actionlabs.carboncalc.model.CalculationInfo;

@Data
public class GetMyCalculationsResultDTO {

    private String userEmail;
    private List<CalculationInfo> calculations;
    
}
