package com.banka1.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "actuary_info")
@Getter
@Setter
@NoArgsConstructor
public class ActuaryInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long employeeId;

    @Column(name = "\"limit\"", precision = 19, scale = 4)
    private BigDecimal limit;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal usedLimit = BigDecimal.ZERO;

    @Column(nullable = false)
    private Boolean needApproval = false;
}
