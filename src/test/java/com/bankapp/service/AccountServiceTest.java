package com.bankapp.service;

import com.bankapp.dto.AccountDto;
import com.bankapp.dto.AccountDtoConverter;
import com.bankapp.dto.CreateAccountRequest;
import com.bankapp.model.Account;
import com.bankapp.model.City;
import com.bankapp.model.Currency;
import com.bankapp.model.Customer;
import com.bankapp.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AccountServiceTest {

    private AccountService accountService;

    private AccountRepository accountRepository;
    private CustomerService customerService;
    private AccountDtoConverter accountDtoConverter;



    @Before
    public void setUp() throws Exception {

        accountRepository = Mockito.mock(AccountRepository.class);
        customerService = Mockito.mock(CustomerService.class);
        accountDtoConverter = Mockito.mock(AccountDtoConverter.class);

        accountService = new AccountService(accountRepository, customerService, accountDtoConverter);
    }

    @Test
    public void whenCreateAccountCalledWithValidRequest_itShouldReturnValidAccountDto(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(5000.0);
        createAccountRequest.setCurrency(Currency.TRY);
        createAccountRequest.setCity(City.IZMIR);

        Customer customer = Customer.builder()
                .id("12345")
                .address("Bornova")
                .city(City.IZMIR)
                .dateOfBirth(1998)
                .name("Beytullah")
                .build();

        Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer);

        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .city(createAccountRequest.getCity())
                .build();

        Mockito.when(accountRepository.save(account)).thenReturn(account);

        AccountDto accountDto = AccountDto.builder()
                .id("12")
                .customerId("12345")
                .balance(5000.0)
                .currency(Currency.TRY)
                .build();

        Mockito.when(accountDtoConverter.convert(account)).thenReturn(accountDto);

        AccountDto result = accountService.createAccount(createAccountRequest);

        //Burada accountDto clasına @EqualsAndHashcode ekledik çünkü 2 farklı objenin
        // kendisini degil degerlerini kıyaslayacagız. Eklemezsek hata alırız
        Assert.assertEquals(result, accountDto); //Sonucun karsılastırılması
        Mockito.verify(customerService).getCustomerById("12345"); //Mocku verify içerisine parametre olarak veririz
        Mockito.verify(accountRepository).save(account);
        Mockito.verify(accountDtoConverter).convert(account);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledWithNonExistCustomer_itShouldReturnEmptyAccountDto(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(5000.0);
        createAccountRequest.setCurrency(Currency.TRY);
        createAccountRequest.setCity(City.IZMIR);

        Mockito.when(customerService.getCustomerById("12345")).thenReturn(Customer.builder().build());

        AccountDto expectedAccountDto = AccountDto.builder().build();

        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result, expectedAccountDto);
        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledWithCustomerWithoutId_itShouldReturnEmptyAccountDto(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(5000.0);
        createAccountRequest.setCurrency(Currency.TRY);
        createAccountRequest.setCity(City.IZMIR);

        Mockito.when(customerService.getCustomerById("12345")).thenReturn(Customer.builder()
                .id(" ")
                .build());

        AccountDto expectedAccountDto = AccountDto.builder().build();

        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result, expectedAccountDto);
        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }
}