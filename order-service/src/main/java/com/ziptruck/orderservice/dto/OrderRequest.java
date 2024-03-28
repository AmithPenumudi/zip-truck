package com.ziptruck.orderservice.dto;



import com.ziptruck.orderservice.model.OrderLineItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long orderId;
    private List<OrderLineItems> orderLineItemsList;
    private Long customerId;
    private Long vendorId;
    private String orderStatus;
}
