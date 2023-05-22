package cs544.ea.OnlineRetailSystem.domain.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class HttpResponse {
    private String message;
    private HttpStatus status;
}
