package com.bankapp;

import com.bankapp.model.Account;
import com.bankapp.model.City;
import com.bankapp.model.Currency;
import com.bankapp.model.Customer;
import com.bankapp.repository.AccountRepository;
import com.bankapp.repository.CustomerRepository;
import com.bankapp.service.AccountService;
import com.bankapp.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class BankAppApplication implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public BankAppApplication(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BankAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Customer c1 = Customer.builder()
                .id("123")
                .name("Beytullah")
                .city(City.SAMSUN)
                .address("Atakum")
                .dateOfBirth(1998)
                .build();

        Customer c2 = Customer.builder()
                .id("1234")
                .name("Merve")
                .city(City.ISTANBUL)
                .address("Besiktas")
                .dateOfBirth(1997)
                .build();

        Customer c3 = Customer.builder()
                .id("12345")
                .name("Furkan")
                .city(City.ANKARA)
                .address("Cankaya")
                .dateOfBirth(2000)
                .build();

        customerRepository.saveAll(Arrays.asList(c1, c2, c3));

        Account a1 = Account.builder()
                .id("1")
                .customerId("123")
                .city(City.SAMSUN)
                .balance(4000.0)
                .currency(Currency.TRY)
                .build();

        Account a2 = Account.builder()
                .id("2")
                .customerId("1234")
                .city(City.ISTANBUL)
                .balance(6000.0)
                .currency(Currency.TRY)
                .build();

        Account a3 = Account.builder()
                .id("3")
                .customerId("12345")
                .city(City.ANTALYA)
                .balance(7000.0)
                .currency(Currency.TRY)
                .build();

        accountRepository.saveAll(Arrays.asList(a1, a2, a3));
    }
}
