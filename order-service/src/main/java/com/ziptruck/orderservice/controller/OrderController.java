package com.ziptruck.orderservice.controller;


import com.ziptruck.orderservice.dto.OrderRequest;
import com.ziptruck.orderservice.dto.OrderResponse;

import com.ziptruck.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {

        return orderService.getAllOrders();
    }
    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrderById(@PathVariable Long orderId) {

        return orderService.getOrderById(orderId);
    }
    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderById(@PathVariable Long orderId) {

        orderService.deleteOrderById(orderId);
    }
    @DeleteMapping("/{orderId}/lineItems/{skuCode}")
    public void deleteOrderLineItemByIdAndSkuCode(@PathVariable Long orderId, @PathVariable String skuCode) {
        orderService.deleteOrderByIdAndSkuCode(orderId, skuCode);
    }
    @PutMapping("/{orderId}/lineItems/{skuCode},{quantity}")
    public void updateOrderByIdAndSkuCode(@PathVariable Long orderId, @PathVariable String skuCode, @PathVariable Integer quantity) {
        orderService.updateOrderByIdAndSkuCode(orderId, skuCode, quantity);
    }
    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrderById(@PathVariable Long orderId, @RequestBody OrderRequest orderRequest) {
        orderService.updateOrderById(orderId, orderRequest);
    }
}
