package sla.ef_07_05.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sla.ef_07_05.Exception.ResourceNotFoundException;
import sla.ef_07_05.model.Card;
import sla.ef_07_05.repository.CardRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    // Получение всех карточек
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    // Получение карточки по ID
    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }

    // Создание новой карточки
    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    // Обновление существующей карточки
    public Card updateCard(Long id, Card card) {
        if (cardRepository.existsById(id)) {
            card.setId(id);
            return cardRepository.save(card);
        } else {
            throw new ResourceNotFoundException("Card not found with id " + id);
        }
    }

    // Удаление карточки
    public void deleteCard(Long id) {
        if (cardRepository.existsById(id)) {
            cardRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Card not found with id " + id);
        }
    }
}
