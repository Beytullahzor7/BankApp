package com.bankapp.repository;

import com.bankapp.model.Account;
import com.bankapp.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {

    //SELECT * FROM account WHERE balance> ${balance}
    List<Account> findAllByBalanceGreaterThan(Double balance);

    //SELECT * FROM account WHERE currency=${currency} and balance<${balance}
    Account findByCurrencyIsAndBalanceLessThan(Currency currency, Double balance);
}

