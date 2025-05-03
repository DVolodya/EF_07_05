package sla.ef_07_05.service;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCards() {
        Card card = new Card();
        card.setOwnerName("John Doe");
        when(cardRepository.findAll()).thenReturn(Arrays.asList(card));

        List<Card> cards = cardService.getAllCards();

        assertEquals(1, cards.size());
        assertEquals("John Doe", cards.get(0).getOwnerName());
    }
}
