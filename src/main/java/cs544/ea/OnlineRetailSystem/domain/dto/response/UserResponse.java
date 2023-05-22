package cs544.ea.OnlineRetailSystem.domain.dto.response;

import cs544.ea.OnlineRetailSystem.domain.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private long id;
    private String name;

    private String email;
    private Roles role;



}