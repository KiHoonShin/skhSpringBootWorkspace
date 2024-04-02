package com.basic.stock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId; // 상품 id
    private Long quantity; // 수량

    @Version
    private Long version;

    public Stock(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getQuantity(){
        return quantity;
    }

    // 재고 감소 로직
    public void decreaseStock(Long quantity){
        if(this.quantity - quantity < 0){
            throw new RuntimeException("재고 수량은 0보다 작ㅇㄹ 수 없습니다");
        }
        this.quantity -= quantity;
    }


}
