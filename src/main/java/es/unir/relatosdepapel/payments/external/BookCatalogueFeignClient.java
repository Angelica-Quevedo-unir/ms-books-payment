package es.unir.relatosdepapel.payments.external;

import es.unir.relatosdepapel.payments.dto.BookAvailabilityResponseDTO;
import es.unir.relatosdepapel.payments.dto.BookDTO;
import es.unir.relatosdepapel.payments.dto.BookStockUpdateRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "MS-BOOKS-CATALOGUE")
public interface BookCatalogueFeignClient {

    @GetMapping("/api/v1/inventory/{isbn}/availability?quantity={quantity}")
    BookAvailabilityResponseDTO checkBookAvailability(@PathVariable String isbn, @PathVariable int quantity);

    @PatchMapping("/api/v1/inventory/{isbn}/stock/reduce")
    void reduceBookStock(@PathVariable String isbn, @RequestBody BookStockUpdateRequestDTO request);

    @PatchMapping("/api/v1/inventory/{isbn}/stock/restore")
    void restoreBookStock(@PathVariable String isbn, @RequestBody BookStockUpdateRequestDTO request);

    @GetMapping("/api/v1/books/isbn/{isbn}")
    BookDTO getBookByIsbn(@PathVariable String isbn);
}
