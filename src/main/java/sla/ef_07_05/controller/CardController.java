package sla.ef_07_05.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.createCard(card));
    }

    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    // Другие эндпоинты для блокировки, активации, удаления карт
}
