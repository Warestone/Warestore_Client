package org.warestore.model;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserAuthentication {
    @NotEmpty
    @Pattern(regexp = "[a-z][a-z0-9]{4,20}")
    private String username;

    @NotEmpty
    @Size(min = 5, max = 12)
    private String password;
}
