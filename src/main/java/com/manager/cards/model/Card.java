package com.manager.cards.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;

/**
 * Entity class for Card Object Relational Mapping
 */
@Getter
@Setter
@ToString
@Entity
@Where(clause = "enabled = 1")
@Table(name = "CARDS")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "card_id")
    private Long id;

    @Column(name = "user_id")
    @Min(0)
    private Long userId;

    @Column(name = "card_number")
    @Size(max = 25, min = 14, message = "{card.number.invalid}")
    @NotEmpty(message="Please enter your card number")
    private String number;

    @Column(name = "expiry_month")
    @Min(1)
    @Max(12)
    @Size(max = 2, min = 1, message = "{card.expiry.month.invalid}")
    @NotEmpty(message="Please enter your card expiration month (MM)")
    private String expiryMonth;

    @Column(name = "expiry_year")
    @Min(19)
    @Max(40)
    @Size(max = 2, min = 2, message = "{card.expiry.year.invalid}")
    @NotEmpty(message="Please enter your card expiration year (YY)")
    private String expiryYear;

    @Column(name = "created_date")
    private Timestamp created;

    @Column(name = "updated_date")
    private Timestamp lastUpdated;

    @Column(name = "enabled")
    private Boolean enabled;
}
