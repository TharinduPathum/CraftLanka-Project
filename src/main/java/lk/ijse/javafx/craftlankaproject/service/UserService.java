package lk.ijse.javafx.craftlankaproject.service;

import lk.ijse.javafx.craftlankaproject.dto.AuthDTO;
import lk.ijse.javafx.craftlankaproject.dto.AuthResponseDTO;
import lk.ijse.javafx.craftlankaproject.dto.RegisterDTO;
import lk.ijse.javafx.craftlankaproject.entity.Role;
import lk.ijse.javafx.craftlankaproject.entity.User;
import lk.ijse.javafx.craftlankaproject.repository.UserRepository;
import lk.ijse.javafx.craftlankaproject.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String saveUser(RegisterDTO registerDTO){
        if (userRepository.findByUsername(registerDTO.getUserName()).isPresent()){
            throw new RuntimeException("Username is already in use");
        }
        User user= User.builder()
                .username(registerDTO.getUserName())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(Role.valueOf(registerDTO.getRole()))
                .build();
        userRepository.save(user);
        return "User registered successfully";
    }
    public AuthResponseDTO authenticate(AuthDTO authDTO){
        User user=userRepository.findByUsername(authDTO.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Username not found"));
        if (!passwordEncoder.matches(authDTO.getPassword(),user.getPassword())){
            throw new BadCredentialsException("Wrong password");
        }
        String token=jwtUtil.generateToken(authDTO.getUsername());
        return new AuthResponseDTO(token);
    }
}
