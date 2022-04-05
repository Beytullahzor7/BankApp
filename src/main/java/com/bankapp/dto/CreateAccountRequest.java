package com.bankapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest extends BaseAccountRequest {

    private String id;
}
