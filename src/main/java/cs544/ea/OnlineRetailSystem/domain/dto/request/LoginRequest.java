package cs544.ea.OnlineRetailSystem.domain.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    private String email;
    private String password;
}
