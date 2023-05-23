package cs544.ea.OnlineRetailSystem.controller;


import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.domain.dto.response.UserResponse;
import cs544.ea.OnlineRetailSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/owner/{id}")
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



}
