package es.unir.relatosdepapel.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private LocalDate publicationDate;
    private String category;
    private String isbn;
    private String description;
    private Double price;
    private String imageUrl;
    private Boolean isDigital;
    private Byte rating;
    private Boolean visibility;
}
