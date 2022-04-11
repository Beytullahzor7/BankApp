package com.bankapp.service;

import com.bankapp.dto.AccountDto;
import com.bankapp.dto.AccountDtoConverter;
import com.bankapp.dto.CreateAccountRequest;
import com.bankapp.dto.UpdateAccountRequest;
import com.bankapp.exception.CustomerNotFoundException;
import com.bankapp.model.Account;
import com.bankapp.model.Customer;
import com.bankapp.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if(customer.getId() == null || customer.getId().trim().equals(""))
            throw new CustomerNotFoundException("Customer Not Found With Id: " + createAccountRequest.getCustomerId());

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
        if(customer.getId() == null || customer.getId().equals(""))
            throw new CustomerNotFoundException("Customer Not Found With Id: " + updateAccountRequest.getCustomerId());

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

    public List<AccountDto> getAllAccounts(){
        List<Account> accountList = accountRepository.findAll();

        return accountList.stream().map(accountDtoConverter::convert).collect(Collectors.toList());
    }

    public AccountDto getAccountById(String id){
        return accountRepository.findById(id)
                .map(accountDtoConverter::convert).orElse(new AccountDto());
    }

    public void deleteAccountById(String id){
        accountRepository.deleteById(id);
    }

    public AccountDto withDrawMoney(String id, Double amount){
        Optional<Account> accountOptional = accountRepository.findById(id);
        accountOptional.ifPresent(account -> {
            if(account.getBalance() > amount){
                account.setBalance(account.getBalance() - amount);
                System.out.println("You took " + amount + account.getCurrency() + " from the bank");
                accountRepository.save(account);
            }else{
                System.out.println("Insufficent funds -> " +
                        "accoundId: " + id + " balance: " + account.getBalance() + " amount " + amount + account.getCurrency() );
            }
        });

        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());
    }

    public AccountDto addMoney(String id, Double amount){
        Optional<Account> accountOptional = accountRepository.findById(id);
        accountOptional.ifPresent(account -> {
                account.setBalance(account.getBalance() + amount);
                System.out.println("You added " + amount + " $ to the bank");
                accountRepository.save(account);
        });

        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());
    }
}
