package sla.ef_07_05.model;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String encryptedCardNumber; // хранится в зашифрованном виде

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Column(nullable = false)
    private BigDecimal balance;

    // геттеры и сеттеры
}

public enum CardStatus {
    ACTIVE,
    BLOCKED,
    EXPIRED
}
