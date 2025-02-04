package es.unir.relatosdepapel.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // Genera getters, setters, toString(), equals(), y hashCode()
@NoArgsConstructor  // Genera el constructor vacío
@AllArgsConstructor  // Genera el constructor con todos los campos
public class PaymentItemRequestDTO {

    private String bookIsbn;
    private Integer quantity;
}
