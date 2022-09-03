package com.fvthree.eshop.orders;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fvthree.eshop.users.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="orders")
public class Order {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
        parameters = {
            @org.hibernate.annotations.Parameter(
                name = "uuid_gen_strategy_class",
                value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
            )
        }
    )
    @Type(type = "pg-uuid")
    @Column(name = "order_id", updatable = false, nullable = false)
    private UUID orderId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItem;

    @Column(nullable = false, name="shipping_address_1")
    private String shippingAddress1;

    @Column(nullable = false, name="shipping_address_2")
    private String shippingAddress2;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zip;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String status;
    
    @Column(nullable = false, name="total_price")
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}