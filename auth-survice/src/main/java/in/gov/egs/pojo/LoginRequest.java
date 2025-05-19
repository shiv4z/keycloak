package in.gov.egs.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	
	@NotBlank(message = "Password must not be empty")
	private String userName;
	
	@NotBlank(message = "Password must not be empty")
	private String userPazz;

}
