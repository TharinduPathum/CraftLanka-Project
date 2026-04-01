package lk.ijse.javafx.craftlankaproject.service;

import lk.ijse.javafx.craftlankaproject.dto.AuthDTO;
import lk.ijse.javafx.craftlankaproject.dto.AuthResponseDTO;
import lk.ijse.javafx.craftlankaproject.dto.RegisterDTO;
import lk.ijse.javafx.craftlankaproject.entity.Cart;
import lk.ijse.javafx.craftlankaproject.entity.Role;
import lk.ijse.javafx.craftlankaproject.entity.User;
import lk.ijse.javafx.craftlankaproject.repository.CartRepository;
import lk.ijse.javafx.craftlankaproject.repository.UserRepository;
import lk.ijse.javafx.craftlankaproject.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Update this method in UserService.java
    public String saveUser(RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already in use");
        }

        // Convert role to uppercase to match Enum (e.g., "customer" -> "CUSTOMER")
        Role userRole = Role.valueOf(registerDTO.getRole().toUpperCase());

        User user = User.builder()
                .name(registerDTO.getName())       // Add this
                .email(registerDTO.getEmail())     // Add this
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(userRole)
                .build();

        userRepository.save(user);

        if (user.getRole() == Role.CUSTOMER) {
            Cart cart = new Cart();
            cart.setCustomer(user);
            cartRepository.save(cart);
        }

        return "User registered successfully";
    }

    // ------------------- LOGIN / AUTH -------------------
    public AuthResponseDTO authenticate(AuthDTO authDTO) {
        User user = userRepository.findByUsername(authDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if (!passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        String token = jwtUtil.generateToken(authDTO.getUsername());

        // Return the token AND the role name (e.g., "CUSTOMER" or "SELLER")
        return new AuthResponseDTO(token, user.getRole().name());
    }


}