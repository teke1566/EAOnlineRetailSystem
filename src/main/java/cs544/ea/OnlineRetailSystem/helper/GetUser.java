package cs544.ea.OnlineRetailSystem.helper;

import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetUser {// a utility function that retrieves the currently authenticated User object from your application's security context.

    @Autowired
    static UserRepository customerRepository;
    public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return customerRepository.findByname(username);
        }

        return null;
    }

}

