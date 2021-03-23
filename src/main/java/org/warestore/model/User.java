package org.warestore.model;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class User {
    private int id;

    @NotEmpty
    @Pattern(regexp = "[a-z][a-z0-9]{4,20}")
    private String username;

    @NotEmpty
    @Size(min = 5, max = 12)
    private String password;

    @NotEmpty
    @Pattern(regexp = "[А-Я][а-я]{1,20}")
    private String firstName;

    @NotEmpty
    @Pattern(regexp = "[А-Я][а-я]{5,20}")
    private String lastName;

    @NotEmpty
    @Pattern(regexp = "[А-Я][а-я]{5,20}")
    private String patronymicName;

    private String role;

    @NotEmpty
    @Pattern(regexp = "[a-z0-9\\\\.]{3,200}@[a-z0-9]{3,20}.?[a-z]{2,20}?")
    private String email;

    @NotEmpty
    @Pattern(regexp = "\\+[0-9]\\([0-9]{3}\\)-[0-9]{3}\\-[0-9]{2}\\-[0-9]{2}")
    private String phoneNumber;

    @NotEmpty
    @Size(min = 10, max = 50)
    private String address;
}
