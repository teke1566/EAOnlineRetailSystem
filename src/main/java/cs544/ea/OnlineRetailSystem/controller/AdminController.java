package cs544.ea.OnlineRetailSystem.controller;


import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.domain.dto.request.OrderStatusRequest;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;
import cs544.ea.OnlineRetailSystem.domain.dto.response.UserResponse;
import cs544.ea.OnlineRetailSystem.service.AdminService;
import cs544.ea.OnlineRetailSystem.service.OrderService;
import cs544.ea.OnlineRetailSystem.util.CustomErrorType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {


    private AdminService adminService;

    private OrderService orderService;
    @Autowired
    public AdminController(AdminService adminService, OrderService orderService) {
        this.adminService = adminService;
        this.orderService = orderService;
    }

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
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id")  long id) {
        adminService.deleteUser(id);
    }

     // GET /api/v1/admin/orders
    // GET /api/v1/admin/orders?orderStatus=PROCESSED
    @GetMapping("/orders")
    public List<OrderResponse> getAllOrders(@RequestParam(required = false) OrderStatus orderStatus) {
        return orderService.getAllOrders(orderStatus);
    }


// GET /api/v1/admin/orders/:orderId
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("orderId") Long orderId) {
        OrderResponse order = orderService.getOrderById(orderId);

        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	// DELETE /api/v1/admin/orders/:orderId
	// delete order by id
	@DeleteMapping("/orders/{orderId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOrderById(@PathVariable Long orderId) {
		orderService.deleteOrderById(orderId);
	}



    @PutMapping("orders/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId, @RequestBody Map<String, String> statusMap) {
        String status = statusMap.get("status");
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        OrderResponse updatedOrder = orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok(updatedOrder);
    }

}
