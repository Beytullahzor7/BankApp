package com.bankapp.service;

import com.bankapp.dto.CreateCustomerRequest;
import com.bankapp.dto.CustomerDto;
import com.bankapp.dto.CustomerDtoConverter;
import com.bankapp.dto.UpdateCustomerRequest;
import com.bankapp.model.City;
import com.bankapp.model.Customer;
import com.bankapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter customerDtoConverter) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
    }

    public CustomerDto createCustomer(CreateCustomerRequest customerRequest){
        Customer customer = new Customer();
        customer.setId(customerRequest.getId());
        customer.setAddress(customerRequest.getAddress());
        customer.setName(customerRequest.getName());
        customer.setDateOfBirth(customerRequest.getDateOfBirth());
        customer.setCity(City.valueOf(customerRequest.getCity().name()));

        customerRepository.save(customer);

        return customerDtoConverter.convert(customer); //EntityToDto
    }

    public List<CustomerDto> gellAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();

        List<CustomerDto> customerDtoList = new ArrayList<>();
        for(Customer temp : customerList){
            customerDtoList.add(customerDtoConverter.convert(temp));
        }
        return customerDtoList;
    }

    public CustomerDto getCustomerDtoById(String id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        return customerOptional.map(customer -> customerDtoConverter.convert(customer)).orElse(new CustomerDto());
    }

    public void deleteCustomerById(String id) {
        customerRepository.deleteById(id);
    }

    public CustomerDto updateCustomerById(String id, UpdateCustomerRequest updateCustomerRequest) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        customerOptional.ifPresent(customer -> {
            customer.setName(updateCustomerRequest.getName());
            customer.setCity(City.valueOf(updateCustomerRequest.getCity().name()));
            customer.setDateOfBirth(updateCustomerRequest.getDateOfBirth());
            customer.setAddress(updateCustomerRequest.getAddress());
            customerRepository.save(customer);
        });

        return customerOptional.map(customerDtoConverter::convert).orElse(new CustomerDto());
    }

    //I'm gonna use it for AccountService
    protected Customer getCustomerById(String id){
        return customerRepository.findById(id).orElse(new Customer());
    }
}