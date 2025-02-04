package es.unir.relatosdepapel.payments.controller;

import es.unir.relatosdepapel.payments.common.exception.ResourceNotFoundException;
import es.unir.relatosdepapel.payments.dto.PaymentRequestDTO;
import es.unir.relatosdepapel.payments.hateoas.PaymentModel;
import es.unir.relatosdepapel.payments.model.Payment;
import es.unir.relatosdepapel.payments.hateoas.PaymentModelAssembler;
import es.unir.relatosdepapel.payments.service.impl.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payments Management", description = "APIs for managing payments of book purchases")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentModelAssembler paymentModelAssembler;

    public PaymentController(PaymentService paymentService, PaymentModelAssembler paymentModelAssembler) {
        this.paymentService = paymentService;
        this.paymentModelAssembler = paymentModelAssembler;
    }

    @Operation(summary = "Create a new payment", description = "Create a new payment")
    @ApiResponse(responseCode = "201", description = "Payment created successfully")
    @PostMapping
    public ResponseEntity<EntityModel<PaymentModel>> createPayment(@RequestBody PaymentRequestDTO request) {
        Payment payment = paymentService.processPayment(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentModelAssembler.toModel(payment));
    }

    @Operation(summary = "Get payment details by payment ID", description = "Retrieve payment details by payment ID")
    @ApiResponse(responseCode = "200", description = "Payment retrieved successfully")
    @GetMapping("/{paymentId}")
    public ResponseEntity<EntityModel<PaymentModel>> getPaymentDetails(@PathVariable Long paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID " + paymentId + " not found"));
        return ResponseEntity.ok(paymentModelAssembler.toModel(payment));
    }

    @Operation(summary = "Cancel payment by payment ID", description = "Cancel a payment")
    @ApiResponse(responseCode = "204", description = "Payment cancelled successfully")
    @PatchMapping("/{paymentId}")
    public ResponseEntity<Void> cancelPayment(@PathVariable Long paymentId) {
        paymentService.cancelPayment(paymentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete payment by payment ID", description = "Delete a payment from the system")
    @ApiResponse(responseCode = "204", description = "Payment deleted successfully")
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    }
}
