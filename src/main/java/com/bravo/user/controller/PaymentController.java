package com.bravo.user.controller;

import com.bravo.user.annotation.SwaggerController;
import com.bravo.user.enumerator.Crud;
import com.bravo.user.exception.BadRequestException;
import com.bravo.user.model.dto.AddressDto;
import com.bravo.user.model.dto.PaymentDto;
import com.bravo.user.model.dto.PaymentSaveDto;
import com.bravo.user.model.filter.UserFilter;
import com.bravo.user.service.PaymentService;
import com.bravo.user.utility.PageUtil;
import com.bravo.user.utility.ValidatorUtil;
import com.bravo.user.validator.UserValidator;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.bravo.user.validator.PaymentValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/payment")
@SwaggerController
public class PaymentController {

    private final PaymentService paymentService;
    private final UserValidator userValidator;
    private final PaymentValidator paymentValidator;

    public PaymentController(PaymentService paymentService, UserValidator userValidator, PaymentValidator paymentValidator) {
        this.paymentService = paymentService;
        this.userValidator = userValidator;
        this.paymentValidator = paymentValidator;
    }

    /* Instructions 2. Do not create the CRUD APIs
    @PostMapping(value = "/create")
    @ResponseBody
    public PaymentDto create(final @RequestBody PaymentSaveDto request, final BindingResult errors)
            throws BindException {
        paymentValidator.validate(Crud.CREATE, request, errors);
        return paymentService.create(request);
    }
    */


    @GetMapping(value = "/retrieve/{userId}")
    @ResponseBody
    public List<PaymentDto> retrieve(final @PathVariable String userId) {
        userValidator.validateId(userId);
        return paymentService.retrieveByUserId(userId);
    }

    //@PostMapping(value = "/retrieve")
    //@ResponseBody
    //public List<PaymentDto> retrieve(
    //        final @RequestBody UserFilter filter,
    //        final @RequestParam(required = false) Integer page,
    //        final @RequestParam(required = false) Integer size,
    //        final HttpServletResponse httpResponse
    //) {
    //    final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
    //    return userService.retrieve(filter, pageRequest, httpResponse);
    //}

    /* Instructions 2. Do not create the CRUD APIs
    @PatchMapping(value = "/update/{id}")
    @ResponseBody
    public PaymentDto update(
            final @PathVariable String id,
            final @RequestBody PaymentSaveDto request,
            final BindingResult errors
    ) throws BindException {
        paymentValidator.validateId(id);
        paymentValidator.validate(Crud.UPDATE, request, errors);
        return paymentService.update(id, request);
    }
    */

    /* Instructions 2. Do not create the CRUD APIs
    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody
    public boolean delete(final @PathVariable String id) {
        paymentValidator.validateId(id);
        return paymentService.delete(id);
    }
    */

}
