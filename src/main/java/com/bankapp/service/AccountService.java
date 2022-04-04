package com.bankapp.service;

import com.bankapp.dto.AccountDto;
import com.bankapp.dto.AccountDtoConverter;
import com.bankapp.dto.CreateAccountRequest;
import com.bankapp.dto.UpdateAccountRequest;
import com.bankapp.model.Account;
import com.bankapp.model.Customer;
import com.bankapp.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter accountDtoConverter;

    public AccountService(AccountRepository accountRepository, CustomerService customerService, AccountDtoConverter accountDtoConverter) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountDtoConverter = accountDtoConverter;
    }

    public AccountDto createAccount(CreateAccountRequest createAccountRequest){
        Customer customer = customerService.getCustomerById(createAccountRequest.getCustomerId());
        if(customer.getId().equals("") || customer.getId() == null)
            return AccountDto.builder().build();

        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .city(createAccountRequest.getCity())
                .build();

        return accountDtoConverter.convert(accountRepository.save(account));
    }

    public AccountDto updateAccount(String id, UpdateAccountRequest updateAccountRequest){
        Customer customer = customerService.getCustomerById(updateAccountRequest.getCustomerId());
        if(customer.getId().equals("") || customer.getId() == null)
            return AccountDto.builder().build();

        Optional<Account> accountOptional = accountRepository.findById(id);
        accountOptional.ifPresent(account -> {
            account.setBalance(updateAccountRequest.getBalance());
            account.setCity(updateAccountRequest.getCity());
            account.setCurrency(updateAccountRequest.getCurrency());
            account.setCustomerId(updateAccountRequest.getCustomerId());

            accountRepository.save(account);
        });

        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());
    }
}
