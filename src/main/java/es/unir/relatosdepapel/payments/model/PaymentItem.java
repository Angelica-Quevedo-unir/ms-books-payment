package es.unir.relatosdepapel.payments.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_items")
@Data
@NoArgsConstructor
public class PaymentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    @JsonBackReference
    private Payment payment;

    @Column(name = "book_isbn", nullable = false)
    private String bookIsbn;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "price_per_unit",nullable = false)
    private Double pricePerUnit;

    // Constructor necesario para el builder
    public PaymentItem(String bookIsbn, Integer quantity, Double pricePerUnit) {
        this.bookIsbn = bookIsbn;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }
}
