package lk.ijse.javafx.craftlankaproject.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String name;
    private String email;
    private String username;
    private String password;
    private String role;
}
