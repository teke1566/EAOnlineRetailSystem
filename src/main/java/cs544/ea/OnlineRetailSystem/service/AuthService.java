package cs544.ea.OnlineRetailSystem.service;


import cs544.ea.OnlineRetailSystem.domain.dto.request.LoginRequest;
import cs544.ea.OnlineRetailSystem.domain.dto.request.RegisterRequest;
import cs544.ea.OnlineRetailSystem.domain.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    void register(RegisterRequest registerRequest);
}
