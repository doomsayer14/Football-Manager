package org.example.footballmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.footballmanager.entity.enums.Position;
import org.javamoney.moneta.Money;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String secondName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Position position;

    /**
     * We are definitely mustn't use primitive types here because they can cause errors when
     * computing numbers with floating points. Although we have BigDecimal for cases like
     * transactions, I think it is good idea to keep currency and amount of money together. Also
     * class Money uses BigDecimal inside it, and can be widely configured if needed.
     */
    @Column(updatable = false, nullable = false)
    private Money transferPrice;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private LocalDate careerStart;

    @ManyToOne(fetch = FetchType.EAGER)
    private Team team;

    //I think it is good idea to keep transfers in the order they were created
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "player")
    private Set<Transfer> transfers = new LinkedHashSet<>();
}