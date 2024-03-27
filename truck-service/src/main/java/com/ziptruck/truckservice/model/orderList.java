package com.ziptruck.truckservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_order_list")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class orderList {
@Id
    private Long orderId;
}
