package es.unir.relatosdepapel.payments.external;

import es.unir.relatosdepapel.payments.dto.BookAvailabilityResponseDTO;
import es.unir.relatosdepapel.payments.dto.BookDTO;
import es.unir.relatosdepapel.payments.dto.BookStockUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookCatalogueExternalService {

    private final BookCatalogueFeignClient feignClient;

    /**
     * Verificar si hay suficiente stock disponible del libro.
     */
        public BookAvailabilityResponseDTO checkBookAvailability(String isbn, int quantity) {
            return feignClient.checkBookAvailability(isbn, quantity);
        }

    /**
     * Reducir el stock del libro después del pago confirmado.
     */
    public void reduceBookStock(String isbn, int quantity) {
        BookStockUpdateRequestDTO requestDTO = new BookStockUpdateRequestDTO(quantity);
        feignClient.reduceBookStock(isbn, requestDTO);
    }

    /**
     * Restaurar el stock del libro después del pago cancelado.
     */
    public void restoreBookStock(String isbn, int quantity) {
        BookStockUpdateRequestDTO requestDTO = new BookStockUpdateRequestDTO(quantity);
        feignClient.restoreBookStock(isbn, requestDTO);
    }

    /**
     * Obtener el precio actual del libro por ISBN.
     */
    public BookDTO getBookByIsbn(String isbn) {
        return feignClient.getBookByIsbn(isbn);
    }
}

