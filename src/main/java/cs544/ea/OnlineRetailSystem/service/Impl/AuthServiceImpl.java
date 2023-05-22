package cs544.ea.OnlineRetailSystem.service.Impl;


import cs544.ea.OnlineRetailSystem.domain.Role;
import cs544.ea.OnlineRetailSystem.domain.Roles;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.domain.dto.request.LoginRequest;
import cs544.ea.OnlineRetailSystem.domain.dto.request.RegisterRequest;
import cs544.ea.OnlineRetailSystem.domain.dto.response.LoginResponse;
import cs544.ea.OnlineRetailSystem.repository.RoleRepository;
import cs544.ea.OnlineRetailSystem.repository.UserRepository;
import cs544.ea.OnlineRetailSystem.service.AuthService;
import cs544.ea.OnlineRetailSystem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {


    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    @Autowired
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication result = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(result.getName());
            final String accessToken = jwtUtil.generateToken(userDetails);
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            long userId = userRepo.findByEmail(userDetails.getUsername()).getId();
            var loginResponse = new LoginResponse(accessToken, roles, userId);
            return loginResponse;
        } catch (BadCredentialsException e) {
            System.out.println("ISSUE" + e.getMessage());
            throw new BadCredentialsException(e.getMessage());

        }
    }


    @Override
    public void register(RegisterRequest registerRequest) {
        try {
            User user = modelMapper.map(registerRequest, User.class);
            user.setName(Optional.ofNullable(registerRequest.getName()).orElse("Default Name"));
            Roles roleValue = registerRequest.getIsOwner() ? Roles.MERCHANT : Roles.CUSTOMER;
            Role role = roleRepo.findByRole(roleValue);
            user.setRole(Collections.singletonList(role));
            user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));

            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}

