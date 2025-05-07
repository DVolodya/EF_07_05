package sla.ef_07_05.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sla.ef_07_05.Exception.ResourceNotFoundException;
import sla.ef_07_05.model.Card;
import sla.ef_07_05.repository.CardRepository;

public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCards() {
        Card card1 = new Card();
        card1.setId(1L);
        card1.setOwnerName("Card 1");

        Card card2 = new Card();
        card2.setId(2L);
        card2.setOwnerName("Card 2");
        when(cardRepository.findAll()).thenReturn(Arrays.asList(card1, card2));

        List<Card> result = cardService.getAllCards();

        assertEquals(2, result.size());
        verify(cardRepository, times(1)).findAll();
    }

    @Test
    public void testGetCardByIdFound() {
        Card card = new Card();
        card.setId(1L);
        card.setOwnerName("Card 1");
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        Optional<Card> result = cardService.getCardById(1L);

        assertTrue(result.isPresent());
        assertEquals("Card 1", result.get().getOwnerName());
        verify(cardRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCardByIdNotFound() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Card> result = cardService.getCardById(1L);

        assertFalse(result.isPresent());
        verify(cardRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateCard() {
        Card card = new Card();
        card.setId(null);
        card.setOwnerName("Card 1");

        Card savedCard = new Card();
        card.setId(1L);
        card.setOwnerName("New Card");
        when(cardRepository.save(any(Card.class))).thenReturn(savedCard);

        Card result = cardService.createCard(card);

        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("New Card", result.getOwnerName());
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    public void testUpdateCardFound() {
        Card card = new Card();
        card.setId(null);
        card.setOwnerName("Updated Card");
        when(cardRepository.existsById(1L)).thenReturn(true);
//        when(cardRepository.save(any(Card.class))).thenReturn(
//                new Card(1L, "Updated Card"));

        Card result = cardService.updateCard(1L, card);

        assertEquals(1L, result.getId());
        assertEquals("Updated Card", result.getOwnerName());
        verify(cardRepository, times(1)).existsById(1L);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    public void testUpdateCardNotFound() {
        Card card = new Card();
        card.setId(null);
        card.setOwnerName("Updated Card");
        when(cardRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            cardService.updateCard(1L, card);
        });

        verify(cardRepository, times(1)).existsById(1L);
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    public void testDeleteCardFound() {
        when(cardRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> cardService.deleteCard(1L));

        verify(cardRepository, times(1)).existsById(1L);
        verify(cardRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCardNotFound() {
        when(cardRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            cardService.deleteCard(1L);
        });

        verify(cardRepository, times(1)).existsById(1L);
        verify(cardRepository, never()).deleteById(anyLong());
    }
}
