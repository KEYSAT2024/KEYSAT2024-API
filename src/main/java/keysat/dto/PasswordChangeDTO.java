package keysat.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class PasswordChangeDTO {
    private String username;
    private String newPassword;
}