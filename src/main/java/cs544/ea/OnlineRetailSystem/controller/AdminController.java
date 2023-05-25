package cs544.ea.OnlineRetailSystem.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;
import cs544.ea.OnlineRetailSystem.domain.dto.response.UserResponse;
import cs544.ea.OnlineRetailSystem.service.AdminService;
import cs544.ea.OnlineRetailSystem.service.OrderService;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private OrderService orderService;


    @GetMapping
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

    /** ORDER APIs that only ADMIN can call */
    
    // GET /api/v1/admin/orders
    // GET /api/v1/admin/orders?orderStatus=PROCESSED
    // get all orders, can be filtered by orderStatus
	@GetMapping("/orders") //worked
	public ResponseEntity<?> getAllOrders(@RequestParam String orderStatus) {
		if (!orderStatus.isEmpty())
			return new ResponseEntity<List<OrderResponse>>(orderService.getOrdersByStatus(orderStatus), HttpStatus.OK);
		return new ResponseEntity<List<OrderResponse>>(orderService.getAllOrders(), HttpStatus.OK);
	}
	
	// GET /api/v1/admin/orders/:orderId
	// get order by id
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
		return new ResponseEntity<OrderResponse>(orderService.getOrderById(orderId), HttpStatus.OK);
	}

	// DELETE /api/v1/admin/orders/:orderId
	// delete order by id
	@DeleteMapping("/orders/{orderId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOrderById(@PathVariable Long orderId) {
		orderService.deleteOrderById(orderId);
	}
	
	// POST /api/v1/admin/order/:orderId
	// update order status by order id
//	@PutMapping("/orders/{orderId}")
//	public ResponseEntity<?> updateOrderStatusByOrderId(@PathVariable Long orderId, @RequestBody OrderStatusRequest orderStatusRequest) {
//		try {
//			return new ResponseEntity<OrderResponse>(orderService.updateOrderStatus(orderId, orderStatusRequest.getOrderStatus()), HttpStatus.OK);
//		} catch (EntityNotFoundException e) {
//			return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
//		}
//	}

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable("orderId") Long orderId,
            @RequestParam("status") OrderStatus status
    ) {
        OrderResponse updatedOrder = adminService.changeOrderStatus(orderId, status);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
}
