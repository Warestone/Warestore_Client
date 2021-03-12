package org.warestore.model;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class User {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String address;
}
