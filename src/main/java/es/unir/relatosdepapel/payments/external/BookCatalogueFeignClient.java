package es.unir.relatosdepapel.payments.external;

import es.unir.relatosdepapel.payments.dto.BookAvailabilityResponseDTO;
import es.unir.relatosdepapel.payments.dto.BookDTO;
import es.unir.relatosdepapel.payments.dto.BookStockUpdateRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-books-catalogue", path = "/book-catalogue")
public interface BookCatalogueFeignClient {

    @GetMapping("/api/v1/inventory/{isbn}/availability?quantity={quantity}")
    BookAvailabilityResponseDTO checkBookAvailability(@PathVariable String isbn, @PathVariable int quantity);

    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/api/v1/inventory/{isbn}/stock/reduce",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    void reduceBookStock(
            @PathVariable String isbn,
            @RequestBody BookStockUpdateRequestDTO request
    );

    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/api/v1/inventory/{isbn}/stock/restore",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    void restoreBookStock(
            @PathVariable String isbn,
            @RequestBody BookStockUpdateRequestDTO request
    );
    @GetMapping("/api/v1/books/isbn/{isbn}")
    BookDTO getBookByIsbn(@PathVariable String isbn);
}
