package es.unir.relatosdepapel.payments.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data  // Genera getters, setters, toString(), equals(), y hashCode()
@NoArgsConstructor  // Genera el constructor vacío
@AllArgsConstructor  // Genera el constructor con todos los campos
public class PaymentRequestDTO {

    private String userId;
    private List<PaymentItemRequestDTO> items;
}
