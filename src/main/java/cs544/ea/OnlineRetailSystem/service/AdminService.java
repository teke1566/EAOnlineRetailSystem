package cs544.ea.OnlineRetailSystem.service;

import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.domain.dto.response.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminService {
    List<UserResponse> findAll();


    List<UserResponse> getAllCustomers();


    User getCustomerById(long id);

    List<UserResponse> getAllMerchants();

    User getMerchantsById(long id);

    void saveUser(User user);
    void deleteUser(long id);

}
