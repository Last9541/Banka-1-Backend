package com.banka1.order.entity;

import com.banka1.order.entity.enums.OrderDirection;
import com.banka1.order.entity.enums.OrderStatus;
import com.banka1.order.entity.enums.OrderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long listingId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType orderType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer contractSize;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal pricePerUnit;

    @Column(precision = 19, scale = 4)
    private BigDecimal limitValue;

    @Column(precision = 19, scale = 4)
    private BigDecimal stopValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderDirection direction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    private Long approvedBy;

    @Column(nullable = false)
    private Boolean isDone = false;

    @Column(nullable = false)
    private LocalDateTime lastModification;

    @Column(nullable = false)
    private Integer remainingPortions;

    @Column(nullable = false)
    private Boolean afterHours;

    @Column(nullable = false)
    private Boolean allOrNone;

    @Column(nullable = false)
    private Boolean margin;

    @Column(nullable = false)
    private Long accountId;

    @PrePersist
    @PreUpdate
    public void updateLastModification() {
        this.lastModification = LocalDateTime.now();
    }
}
