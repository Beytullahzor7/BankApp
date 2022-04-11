package com.bankapp.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode

@Entity
@Table(name = "account")
public class Account {

    @Id
    private String id;

    private String customerId;
    private Double balance;
    private City city;
    private Currency currency;
}
