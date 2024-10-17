package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Order;
import za.ac.cput.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            Order createdOrder = orderService.create(order);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            log.error("Invalid order data: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error creating order: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable long id) {
        try {
            Order order = orderService.read(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (IllegalStateException e) {
            log.error("Order not found: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error fetching order: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
        try {
            Order updatedOrder = orderService.update(order);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (IllegalStateException e) {
            log.error("Order not found for update: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error updating order: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable long id) {
        try {
            boolean deleted = orderService.delete(id);
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        } catch (IllegalStateException e) {
            log.error("Order not found for deletion: {}", e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting order: ", e);
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderService.getAll();
            log.info("Fetched orders: {}", orders);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching orders: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable long userId) {
        log.info("Received request to fetch orders for user ID: {}", userId);

        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);

            // Log the fetched orders for debugging
            log.info("Fetched orders for user ID {}: {}", userId, orders);

            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (IllegalStateException e) {
            log.error("No orders found for user ID {}: {}", userId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error fetching orders for user ID {}: ", userId, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/approve/{id}")
    public ResponseEntity<Order> approveOrder(@PathVariable long id) {
        try {
            Order existingOrder = orderService.read(id);
            if (existingOrder != null) {
                Order updatedOrder = new Order.Builder()
                        .copy(existingOrder)
                        .setStatus("approved")
                        .build();

                return updateOrder(updatedOrder);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error approving order: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/decline/{id}")
    public ResponseEntity<Order> declineOrder(@PathVariable long id) {
        try {
            Order existingOrder = orderService.read(id);
            if (existingOrder != null) {
                Order updatedOrder = new Order.Builder()
                        .copy(existingOrder)
                        .setStatus("declined")
                        .build();

                return updateOrder(updatedOrder);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error declining order: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

