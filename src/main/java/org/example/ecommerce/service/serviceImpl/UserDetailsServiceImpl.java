package org.example.ecommerce.service.serviceImpl;


import lombok.RequiredArgsConstructor;
import org.example.ecommerce.custom.CustomUserDetails;
import org.example.ecommerce.model.User;
import org.example.ecommerce.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Foydalanuvchi topilmadi!"));
        return new CustomUserDetails(user);
    }
}
