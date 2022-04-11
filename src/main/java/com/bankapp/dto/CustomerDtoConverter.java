package com.bankapp.dto;

import com.bankapp.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoConverter {

    public CustomerDto convert(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        //customerDto.setAddress(customer.getAddress());
        customerDto.setName(customer.getName());
        customerDto.setDateOfBirth(customer.getDateOfBirth());
        customerDto.setCity(CityDto.valueOf(customer.getCity().name()));

        return customerDto;
    }
}
