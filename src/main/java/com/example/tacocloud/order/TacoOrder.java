package com.example.tacocloud.order;

import com.example.tacocloud.tacos.Taco;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import lombok.Data;

@Data
@Entity
@Table(name="taco_order")
public class TacoOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placed_at")
    private Date placedAt = new Date();

    @NotBlank(message="Delivery name is required")
    @Column(name = "delivery_name")
    private String deliveryName;

    @NotBlank(message="Delivery street is required")
    @Column(name = "delivery_street")
    private String deliveryStreet;

    @NotBlank(message="Delivery city is required")
    @Column(name = "delivery_city")
    private String deliveryCity;

    @NotBlank(message="Delivery state is required")
    @Column(name = "delivery_state")
    private String deliveryState;

    @NotBlank(message="Delivery zip is required")
    @Column(name = "delivery_zip")
    private String deliveryZip;

    @CreditCardNumber(message="Not a valid credit card number")
    @Column(name = "cc_number")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",message="Must be formatted MM/YY")
    @Column(name = "cc_expiration")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Invalid cvv")
    @Column(name = "cc_cvv")
    private String ccCVV;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="taco_order_id")
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco){
        this.tacos.add(taco);
    }
}
