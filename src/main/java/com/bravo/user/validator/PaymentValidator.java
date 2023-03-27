package com.bravo.user.validator;

import com.bravo.user.exception.BadRequestException;
import com.bravo.user.model.dto.PaymentSaveDto;
import com.bravo.user.utility.ValidatorUtil;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PaymentValidator extends CrudValidator {

    public void validateId(String id){
        if(ValidatorUtil.isInvalid(id)){
            throw new BadRequestException("'id' is required");
        }
    }
    public void validateCardNumber(String cardNumber){
        if(ValidatorUtil.isInvalid(cardNumber)){
            throw new BadRequestException("'cardNumber' is required");
        }
        if(!cardNumber.matches("^\\d{16}$")){
            throw new BadRequestException("'cardNumber' is required and must contains 16 numeric digits");
        }

    }


    @Override
    protected void validateCreate(Object o, Errors errors) {

        PaymentSaveDto instance = (PaymentSaveDto) o;

        // required fields
        if(ValidatorUtil.isInvalid(instance.getUserId())){
            errors.reject("'userId' is required");
        }
        if(ValidatorUtil.isInvalid(instance.getExpiryMonth())){
            errors.reject("'expiryMonth' is required");
        }
        if(ValidatorUtil.isInvalid(instance.getExpiryYear())){
            errors.reject("'expiryYear' is required");
        }

        if(Objects.isNull(instance.getCardNumber()) || !instance.getCardNumber().matches("^\\d{16}$")){
            errors.reject("'cardNumber' of format d{16} is required");
        }
    }

    //@Override
    //protected void validateUpdate(Object o, Errors errors) {

    //    UserSaveDto instance = (UserSaveDto) o;

    //    if(ValidatorUtil.isEmpty(instance, "id", "updated")){
    //        errors.reject("'request' modifiable field(s) are required");
    //    }

    //    if(Objects.nonNull(instance.getPhoneNumber()) && !instance.getPhoneNumber().matches("[0-9]{10}")){
    //        errors.reject("'phoneNumber' of format [0-9]{10} is required");
    //    }
    //}
}
