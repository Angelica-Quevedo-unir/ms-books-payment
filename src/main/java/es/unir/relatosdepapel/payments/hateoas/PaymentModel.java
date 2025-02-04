package es.unir.relatosdepapel.payments.hateoas;

import es.unir.relatosdepapel.payments.common.enums.PaymentStatus;
import es.unir.relatosdepapel.payments.model.Payment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentModel extends RepresentationModel<PaymentModel> {

    private Long id;
    private String userId;
    private Double totalAmount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private LocalDateTime paymentDate;

    public PaymentModel(Payment payment) {
        this.id = payment.getId();
        this.userId = payment.getUserId();
        this.totalAmount = payment.getTotalAmount();
        this.status = payment.getStatus();
        this.paymentDate = getPaymentDate();
    }
}
