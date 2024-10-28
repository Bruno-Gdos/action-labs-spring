package br.com.actionlabs.carboncalc.dto;

import br.com.actionlabs.carboncalc.enums.TransportationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransportationDTO {
  private TransportationType type;
  private int monthlyDistance;
}
