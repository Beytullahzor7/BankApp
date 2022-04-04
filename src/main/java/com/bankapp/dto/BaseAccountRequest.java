package com.bankapp.dto;

import com.bankapp.model.City;
import com.bankapp.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseAccountRequest {

    private String customerId;
    private Double balance;
    private Currency currency;
    private City city;
}
