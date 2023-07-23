package com.Ankit.Expense.Tracker.API.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Expense {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;
private LocalDate expenseDateStamp;

@ManyToOne
@JoinColumn(name = "fk_userId")
private User user;
@ManyToMany
@JoinTable(name = "expense_product",joinColumns = @JoinColumn(name = "fk_expenseId"),inverseJoinColumns = @JoinColumn(name = "fk_productId"))
private List<Product> product;

}
