package br.com.actionlabs.carboncalc.dto;

import lombok.Data;
import java.util.List;

import br.com.actionlabs.carboncalc.model.Calculation;

@Data
public class GetMyCalculationsResultDTO {

    private String userEmail;
    private List<Calculation> calculations;
    
}
