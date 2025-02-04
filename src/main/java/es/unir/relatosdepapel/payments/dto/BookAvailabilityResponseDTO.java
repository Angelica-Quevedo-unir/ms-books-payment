package es.unir.relatosdepapel.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAvailabilityResponseDTO {
    private boolean available;
    private int availableStock;
    private String message;
}
