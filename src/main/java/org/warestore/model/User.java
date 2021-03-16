package org.warestore.model;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class User {
    private int id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private String role;
    @NotEmpty
    private String email;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String address;
}
