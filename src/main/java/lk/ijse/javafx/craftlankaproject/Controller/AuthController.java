package lk.ijse.javafx.craftlankaproject.Controller;

import lk.ijse.javafx.craftlankaproject.dto.APIResponse;
import lk.ijse.javafx.craftlankaproject.dto.AuthDTO;
import lk.ijse.javafx.craftlankaproject.dto.RegisterDTO;
import lk.ijse.javafx.craftlankaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    @PostMapping("signup")
    public ResponseEntity<APIResponse> saveUser(@RequestBody RegisterDTO registerDTO){
        return ResponseEntity.ok(new APIResponse(
                200,"User registered successfully",userService.saveUser(registerDTO)));

    }
    @PostMapping("signin")
    public ResponseEntity<APIResponse>loginUser(@RequestBody AuthDTO authDTO){
        return ResponseEntity.ok(new APIResponse(
                200,"User registered successfully",userService.authenticate(authDTO)));

    }
}
