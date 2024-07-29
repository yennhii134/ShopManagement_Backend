package com.edu.iuh.shop_managerment.services;

import com.edu.iuh.shop_managerment.dto.request.OrderAndOrderItemRequest;
import com.edu.iuh.shop_managerment.dto.request.OrderItemRequest;
import com.edu.iuh.shop_managerment.dto.respone.OrderAndOrderItemRespone;
import com.edu.iuh.shop_managerment.dto.respone.OrderItemRespone;
import com.edu.iuh.shop_managerment.enums.order.Status;
import com.edu.iuh.shop_managerment.mappers.OrderMapper;
import com.edu.iuh.shop_managerment.models.Order;
import com.edu.iuh.shop_managerment.models.OrderItem;
import com.edu.iuh.shop_managerment.models.User;
import com.edu.iuh.shop_managerment.repositories.OrderItemRepository;
import com.edu.iuh.shop_managerment.repositories.OrderRepository;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderService {
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    UserService userService;
    OrderItemRepository orderItemRepository;
    EmailService emailService;

    public OrderAndOrderItemRespone addOrder(OrderAndOrderItemRequest orderAndOrderItemRequest) throws MessagingException {
        log.info("OrderAndOrderItemRequest: {}", orderAndOrderItemRequest);
        User user = userService.getCurrentUser();
        Order order = orderMapper.orderResponeToOrder(orderAndOrderItemRequest.getOrderRequest());
        order.setUserId(user.getId());
        order.setOrderDate(new Date(System.currentTimeMillis()));
        order.setOrderStatus(Status.CREATED);
        orderRepository.save(order);

        for (OrderItemRequest orderItemRequest : orderAndOrderItemRequest.getOrderItemRequests()) {
            OrderItem orderItem = OrderItem.builder()
                    .cartId(orderItemRequest.getCartId())
                    .productName(orderItemRequest.getProductName())
                    .productPrice(orderItemRequest.getProductPrice())
                    .productQuantity(orderItemRequest.getProductQuantity())
                    .productTotalPrice(orderItemRequest.getProductTotalPrice())
                    .productColor(orderItemRequest.getProductColor())
                    .build();
            orderItem.setOrderId(order.getId());
            addOrderItem(orderItem);
        }
        order.setOrderItems(orderAndOrderItemRequest.getOrderItemRequests()
                .stream().map(orderMapper::orderItemRequestToOrderItem).collect(Collectors.toList()));

        emailService.sendPaymentConfirmationEmail(user.getUserName(), user.getFullName(), order.getOrderTotal());

        return generateOrder(order);
    }

    public OrderAndOrderItemRespone getOrder(String id) {
        Order order = orderRepository.findById(Long.valueOf(id)).orElse(null);
        return generateOrder(order);
    }
    public List<OrderAndOrderItemRespone> getOrders() {
        return orderRepository.findAll().stream().map(this::generateOrder).collect(Collectors.toList());
    }
    public OrderAndOrderItemRespone generateOrder(Order order) {
        return order == null ? null : OrderAndOrderItemRespone.builder()
                .orderRespone(orderMapper.toOrderRespone(order))
                .orderItemRespone(order.getOrderItems().stream().map(orderMapper::orderItemToOrderItemRespone).collect(Collectors.toList()))
                .build();
    }
    private void addOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public OrderItemRespone getOrderItem(String id) {
        return orderItemRepository.findById(Long.valueOf(id)).map(orderMapper::orderItemToOrderItemRespone).orElse(null);
    }
}
