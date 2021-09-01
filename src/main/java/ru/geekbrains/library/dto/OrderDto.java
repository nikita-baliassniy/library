package ru.geekbrains.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.library.model.Order;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;
    @JsonProperty("name")
    private String ownerUserInfoName;
    private double price;
//    private String address;
    private LocalDateTime createdAt;

//    public OrderDto(Order order) {
//        this.id = order.getId();
////        this.username = order.getOwner().getUsername();
//        this.price = order.getPrice();
////        this.address = order.getAddress();
//        this.creationDateTime = order.getCreatedAt().toString();
//    }
}
