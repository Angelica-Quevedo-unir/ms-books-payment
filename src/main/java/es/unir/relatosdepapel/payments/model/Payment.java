package es.unir.relatosdepapel.payments.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import es.unir.relatosdepapel.payments.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "payment", orphanRemoval = true)
    @JsonManagedReference
    private List<PaymentItem> items = new ArrayList<>();

    public Payment(String userId, List<PaymentItem> items, Double totalAmount,
                   PaymentStatus status, LocalDateTime paymentDate, LocalDateTime createdAt) {
        this.userId = userId;

        if (items != null) {
            items.forEach(this::addItem);
        }

        this.totalAmount = totalAmount;
        this.status = status;
        this.paymentDate = paymentDate;
        this.createdAt = createdAt;
    }

    public void addItem(PaymentItem item) {
        items.add(item);
        item.setPayment(this);
    }

    public void removeItem(PaymentItem item) {
        items.remove(item);
        item.setPayment(null);
    }
}