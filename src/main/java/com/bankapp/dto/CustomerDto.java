package com.bankapp.dto;

import com.bankapp.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private String id;
    private String name;
    private Integer dateOfBirth;
    private CityDto city;
    private Address address;
}
