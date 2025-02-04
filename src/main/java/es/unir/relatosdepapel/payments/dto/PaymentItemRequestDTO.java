package es.unir.relatosdepapel.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentItemRequestDTO {

    private String bookIsbn;
    private Integer quantity;
}
