package com.bravo.user.dao.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;

import com.bravo.user.model.dto.PaymentSaveDto;
import lombok.Data;

@Entity
@Data
@Table(name = "payment")
public class Payment {

  @Id
  @Column(name = "id")
  private String id;

  //@JoinColumn(name = "user_id", table = "user", referencedColumnName = "id", nullable = false)
  @Column(name = "user_id")
  private String userId;

  @Column(name = "card_number", nullable = false)
  private String cardNumber;

  @Column(name = "expiry_month", nullable = false)
  private Integer expiryMonth;

  @Column(name = "expiry_year", nullable = false)
  private Integer expiryYear;

  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  public Payment(){
    super();
    this.id = UUID.randomUUID().toString();
    this.updated = LocalDateTime.now();
  }

  public Payment(final PaymentSaveDto payment){
    this();
    this.cardNumber = payment.getCardNumber();
    this.expiryMonth = payment.getExpiryMonth();
    this.expiryYear = payment.getExpiryYear();
  }
}
