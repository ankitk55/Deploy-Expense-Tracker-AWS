package com.Ankit.Expense.Tracker.API.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productTitle;
    private String productDescription;
    private Double productPrice;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate productCreationDateStamp;

    @ManyToOne
    @JoinColumn(name = "fk_userId")
    private User user;

}
