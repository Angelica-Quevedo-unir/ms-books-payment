package es.unir.relatosdepapel.payments.hateoas;

import es.unir.relatosdepapel.payments.common.enums.PaymentStatus;
import es.unir.relatosdepapel.payments.controller.PaymentController;
import es.unir.relatosdepapel.payments.model.Payment;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaymentModelAssembler implements RepresentationModelAssembler<Payment, EntityModel<PaymentModel>> {

    @Override
    public EntityModel<PaymentModel> toModel(Payment payment) {
        PaymentModel paymentModel = new PaymentModel(payment);

        paymentModel.add(linkTo(methodOn(PaymentController.class).getPaymentDetails(payment.getId())).withSelfRel());

        if (payment.getStatus() == PaymentStatus.PENDING) {
            paymentModel.add(linkTo(methodOn(PaymentController.class).cancelPayment(payment.getId())).withRel("cancel"));
        }

        return EntityModel.of(paymentModel);
    }
}

