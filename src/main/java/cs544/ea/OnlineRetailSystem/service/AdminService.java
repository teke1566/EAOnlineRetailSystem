package cs544.ea.OnlineRetailSystem.service;

import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;
import cs544.ea.OnlineRetailSystem.domain.dto.response.UserResponse;

import java.util.List;

public interface AdminService {
    List<UserResponse> findAll();


    List<UserResponse> getAllCustomers();


    User getCustomerById(long id);

    List<UserResponse> getAllMerchants();
    User getMerchantsById(long id);
    OrderResponse changeOrderStatus(Long orderId, OrderStatus orderStatus);

    void saveUser(User user);
    void deleteUser(long id);

}
