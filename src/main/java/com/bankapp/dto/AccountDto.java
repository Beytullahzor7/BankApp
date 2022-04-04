package com.bankapp.dto;

import com.bankapp.model.Currency;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    private String id;
    private String customerId;
    private Double balance;
    private Currency currency;
}
