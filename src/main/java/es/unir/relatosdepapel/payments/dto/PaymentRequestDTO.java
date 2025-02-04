package es.unir.relatosdepapel.payments.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {

    private String userId;
    private List<PaymentItemRequestDTO> items;
}
