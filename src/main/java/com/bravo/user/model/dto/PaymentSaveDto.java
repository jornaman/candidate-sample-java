package com.bravo.user.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentSaveDto {

    private String userId;
    private String cardNumber;
    private Integer expiryMonth;
    private Integer expiryYear;

}
