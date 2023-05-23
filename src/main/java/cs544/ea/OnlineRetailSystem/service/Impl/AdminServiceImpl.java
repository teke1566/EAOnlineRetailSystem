package cs544.ea.OnlineRetailSystem.service.Impl;

import cs544.ea.OnlineRetailSystem.domain.Roles;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.domain.dto.response.UserResponse;
import cs544.ea.OnlineRetailSystem.repository.UserRepository;
import cs544.ea.OnlineRetailSystem.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AdminServiceImpl implements AdminService {
    private UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Autowired

    public AdminServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserResponse> findAll() {

        List<UserResponse> users = new ArrayList<>();
        userRepository.findAllExceptAdmin().forEach(u -> {
            UserResponse user = modelMapper.map(u, UserResponse.class);
            user.setRoles(u.getRole().get(0).getRole());
            users.add(user);
        });
        return users;
    }


    @Override
    public List<UserResponse> getAllCustomers() {
        List<UserResponse> users = findAll()
                .stream()
                .filter(lst -> lst.getRoles().equals(Roles.CUSTOMER))
                .toList();
        return  users;    }

    @Override
    public User getCustomerById(long id) {
        return null;
    }

    @Override
    public List<UserResponse> getAllMerchants() {
        List<UserResponse> users = findAll()
                .stream()
                .filter(lst -> lst.getRoles().equals(Roles.MERCHANT))
                .toList();
        return  users;    }

    @Override
    public User getMerchantsById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

}
