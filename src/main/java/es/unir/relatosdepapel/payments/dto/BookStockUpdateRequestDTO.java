package es.unir.relatosdepapel.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookStockUpdateRequestDTO {
    private int quantity;
}