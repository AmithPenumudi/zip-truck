package com.ziptruck.orderservice.service;

import com.ziptruck.orderservice.dto.CatalogueResponse;
import com.ziptruck.orderservice.dto.OrderLineItemsDto;
import com.ziptruck.orderservice.dto.OrderRequest;
import com.ziptruck.orderservice.dto.OrderResponse;
import com.ziptruck.orderservice.model.Order;
import com.ziptruck.orderservice.model.OrderLineItems;
import com.ziptruck.orderservice.repository.OrderRepository;
import com.ziptruck.vendorservice.service.VendorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@ComponentScan(basePackages = "com.ziptruck.vendorservice")
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;
    private final VendorService vendorService;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItems);
        order.setCustomerId(orderRequest.getCustomerId());
        order.setVendorId(orderRequest.getVendorId());
        order.setOrderStatus(orderRequest.getOrderStatus());
        List<String> itemNames = order.getOrderLineItemsList().stream()
                        .map(OrderLineItems::getItemName)
                                .toList();
        Long vendorId = order.getVendorId();
        CatalogueResponse[] CatalogueResponseArray =  webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("http://localhost:8080/api/catalogue")
                        .queryParam("vendorId", vendorId)
                        .queryParam("itemNames", itemNames)
                        .build())
                .retrieve()
                .bodyToMono(CatalogueResponse[].class)
                .block();
        boolean allItemsInCatalogue = Arrays.stream(CatalogueResponseArray)
                .allMatch(CatalogueResponse::isInCatalogue);
        if (allItemsInCatalogue) {
            boolean isAccepted= vendorService.isAccepting(vendorId, order.getCustomerId(), order.getOrderId());
            if(isAccepted){
                log.info("Order {} is accepted by vendor {}", order.getOrderId(), vendorId);
                order.setOrderStatus("ACCEPTED");
            }else{
                log.info("Order {} is rejected by vendor {}", order.getOrderId(), vendorId);
                order.setOrderStatus("REJECTED");
            }
            orderRepository.save(order);
        }else{
            throw new RuntimeException("Order cannot be placed as some items are not in the catalogue");
        }

    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }
    public OrderResponse getOrderById(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.map(this::mapToOrderResponse)
                .orElseThrow(() -> new RuntimeException("Catalogue not found with id: " + orderId));
    }
    @Transactional
    public void deleteOrderById(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            log.info("Order with id {} has been deleted", orderId);
        } else {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
    }



    @Transactional
    public void deleteOrderByIdAndSkuCode(Long orderId, String skuCode) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            List<OrderLineItems> orderLineItemsList = order.getOrderLineItemsList();
            // Find and remove the order line item with the given skuCode
            Optional<OrderLineItems> lineItemToRemove = orderLineItemsList.stream()
                    .filter(item -> item.getItemName().equals(skuCode))
                    .findFirst();
            lineItemToRemove.ifPresent(orderLineItems -> {
                orderLineItemsList.remove(orderLineItems);
                // If order line items list becomes empty, delete the order
                if (orderLineItemsList.isEmpty()) {
                    orderRepository.deleteById(orderId);
                    log.info("Order with id {} has been deleted as its line items list became empty", orderId);
                } else {
                    // Save the order after removing the line item
                    orderRepository.save(order);
                    log.info("Order line item with skuCode {} has been deleted from order with id {}", skuCode, orderId);
                }
            });
        } else {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
    }


        @Transactional
        public void updateOrderByIdAndSkuCode(Long orderId, String skuCode, Integer quantity) {
            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                List<OrderLineItems> orderLineItemsList = order.getOrderLineItemsList();
                // Find the order line item with the given skuCode
                Optional<OrderLineItems> lineItemToUpdate = orderLineItemsList.stream()
                        .filter(item -> item.getItemName().equals(skuCode))
                        .findFirst();
                if (lineItemToUpdate.isPresent()) {
                    // Update the found order line item
                    OrderLineItems orderLineItem = lineItemToUpdate.get();
                    orderLineItem.setQuantity(quantity);
                    // Save the order after updating the line item
                    orderRepository.save(order);
                    log.info("Order line item with skuCode {} has been updated in order with id {}", skuCode, orderId);
                } else {
                    throw new RuntimeException("Order line item with skuCode " + skuCode + " not found in order with id: " + orderId);
                }
            } else {
                throw new RuntimeException("Order not found with id: " + orderId);
            }
        }



    @Transactional
    public void updateOrderById(Long orderId, OrderRequest orderRequest) {
        Optional<Order> optionalCatalogue = orderRepository.findById(orderId);
        if (optionalCatalogue.isPresent()) {
            Order order = optionalCatalogue.get();


            if (orderRequest.getOrderLineItemsDtoList() != null) {
                List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                        .stream()
                        .map(this::mapToDto)
                        .collect(Collectors.toList());
                order.setOrderLineItemsList(orderLineItems);
            }
            if (orderRequest.getCustomerId() != null) {
                order.setCustomerId(orderRequest.getCustomerId());
            }
            if (orderRequest.getVendorId() != null) {
                order.setVendorId(orderRequest.getVendorId());
            }
            if (orderRequest.getOrderStatus() != null) {
                order.setOrderStatus(orderRequest.getOrderStatus());
            }

            orderRepository.save(order);
            log.info("Order with id {} has been updated", orderId);
        } else {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
    }
private OrderResponse mapToOrderResponse(Order order){
    return new OrderResponse(
            order.getOrderId(),
            order.getOrderLineItemsList(),
            order.getCustomerId(),
            order.getVendorId(),
            order.getOrderStatus()
    );
}
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setItemName(orderLineItemsDto.getItemName());
        return orderLineItems;
    }
}
