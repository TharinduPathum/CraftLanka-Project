package lk.ijse.javafx.craftlankaproject.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.javafx.craftlankaproject.repository.CartRepository;
import lk.ijse.javafx.craftlankaproject.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public void clearCartByCustomerEmail(String email) {
        cartRepository.deleteByCustomerEmail(email);
    }
}
