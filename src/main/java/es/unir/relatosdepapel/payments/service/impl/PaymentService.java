package es.unir.relatosdepapel.payments.service.impl;

import es.unir.relatosdepapel.payments.common.enums.PaymentStatus;
import es.unir.relatosdepapel.payments.common.exception.InsufficientStockException;
import es.unir.relatosdepapel.payments.common.exception.PaymentCancellationException;
import es.unir.relatosdepapel.payments.common.exception.ResourceNotFoundException;
import es.unir.relatosdepapel.payments.common.exception.StockUpdateException;
import es.unir.relatosdepapel.payments.dto.BookAvailabilityResponseDTO;
import es.unir.relatosdepapel.payments.dto.BookDTO;
import es.unir.relatosdepapel.payments.dto.PaymentItemRequestDTO;
import es.unir.relatosdepapel.payments.dto.PaymentRequestDTO;
import es.unir.relatosdepapel.payments.external.BookCatalogueExternalService;
import es.unir.relatosdepapel.payments.model.Payment;
import es.unir.relatosdepapel.payments.model.PaymentItem;
import es.unir.relatosdepapel.payments.repository.PaymentRepository;
import es.unir.relatosdepapel.payments.service.IPaymentService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final BookCatalogueExternalService bookCatalogueExternalService;

    @Override
    public Payment processPayment(PaymentRequestDTO request) {
        validateBooks(request);

        List<PaymentItem> items = createPaymentItems(request);
        double totalAmount = calculateTotalAmount(items);

        Payment payment = createPayment(request.getUserId(), items, totalAmount);
        paymentRepository.save(payment);

        reduceStockForPayment(items);
        return payment;
    }

    @Override
    public Optional<Payment> getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public void cancelPayment(Long paymentId) {
        Payment payment = getPaymentById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID " + paymentId + " not found"));

        validateCancellation(payment);
        restoreStockForPayment(payment);
        updatePaymentStatusToCancelled(payment);
    }

    private List<PaymentItem> createPaymentItems(PaymentRequestDTO request) {
        return request.getItems().stream()
                .map(item -> new PaymentItem(item.getBookIsbn(), item.getQuantity(), fetchBookPrice(item.getBookIsbn())))
                .collect(Collectors.toList());
    }

    private double calculateTotalAmount(List<PaymentItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPricePerUnit())
                .sum();
    }

    private Payment createPayment(String userId, List<PaymentItem> items, double totalAmount) {
        Payment payment = new Payment(userId, items, totalAmount, PaymentStatus.PENDING, LocalDateTime.now(), LocalDateTime.now());
        items.forEach(item -> item.setPayment(payment));
        return payment;
    }

    private void reduceStockForPayment(List<PaymentItem> items) {
        items.forEach(item -> {
            try {
                bookCatalogueExternalService.reduceBookStock(item.getBookIsbn(), item.getQuantity());
            } catch (Exception e) {
                throw new StockUpdateException("Failed to reduce stock for book with ISBN " + item.getBookIsbn(), e);
            }
        });
    }

    private void validateCancellation(Payment payment) {
        if (PaymentStatus.COMPLETED.equals(payment.getStatus())) {
            throw new PaymentCancellationException("Cannot cancel a completed payment with ID " + payment.getId());
        }
    }

    private void restoreStockForPayment(Payment payment) {
        payment.getItems().forEach(item ->
                bookCatalogueExternalService.restoreBookStock(item.getBookIsbn(), item.getQuantity()));
    }

    private void updatePaymentStatusToCancelled(Payment payment) {
        payment.setStatus(PaymentStatus.CANCELLED);
        paymentRepository.save(payment);
    }

    private void validateBooks(PaymentRequestDTO request) {
        request.getItems().forEach(item -> {
            try {
                BookAvailabilityResponseDTO response = bookCatalogueExternalService.checkBookAvailability(item.getBookIsbn(), item.getQuantity());
                if (!response.isAvailable()) {
                    throw new InsufficientStockException(
                            String.format("Not enough stock for ISBN %s. Requested: %d, Available: %d",
                                    item.getBookIsbn(), item.getQuantity(), response.getAvailableStock())
                    );
                }
            } catch (FeignException.BadRequest e) {
                throw new RuntimeException("Error 400 during stock validation: " + e.contentUTF8(), e);
            } catch (Exception e) {
                throw new RuntimeException("Unexpected error during stock validation for ISBN " + item.getBookIsbn(), e);
            }
        });
    }

    private Double fetchBookPrice(String bookIsbn) {
        try {
            BookDTO book = bookCatalogueExternalService.getBookByIsbn(bookIsbn);
            if (book == null) {
                throw new ResourceNotFoundException("Book with ISBN " + bookIsbn + " not found");
            }
            return book.getPrice();
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Book with ISBN " + bookIsbn + " not found");
        }
    }
}


