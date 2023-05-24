package cs544.ea.OnlineRetailSystem.controller;


import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;
import cs544.ea.OnlineRetailSystem.domain.dto.response.UserResponse;
import cs544.ea.OnlineRetailSystem.service.AdminService;
import cs544.ea.OnlineRetailSystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> findAll() {
        return adminService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/customer")
    public List<UserResponse> getAllCustomer(){
        return adminService.getAllCustomers();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/customer/{id}")
    public User getCustomerById(@PathVariable("id") long id){
        return adminService.getCustomerById(id);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/merchants")
    public List<UserResponse> getAllMerchants(){
        return adminService.getAllMerchants();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/merchants/{id}")
    public User getOwnerById(@PathVariable("id") long id){
        return adminService.getMerchantsById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void saveUser(@RequestBody User user) {
        adminService.saveUser(user);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id")  long id) {
        adminService.deleteUser(id);
    }


    @GetMapping
    public List<OrderResponse> getAllOrders(@RequestParam OrderStatus orderStatus) {
        if (orderStatus != null)
            return orderService.getOrdersByStatus(orderStatus);
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable("orderId") Long orderId,
            @RequestParam("status") OrderStatus status
    ) {
        OrderResponse updatedOrder = adminService.changeOrderStatus(orderId, status);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
}
