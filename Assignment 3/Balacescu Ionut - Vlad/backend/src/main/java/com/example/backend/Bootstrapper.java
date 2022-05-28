package com.example.backend;

import com.example.backend.game.GameRepository;
import com.example.backend.game.GameService;
import com.example.backend.security.AuthService;
import com.example.backend.security.dto.SignupRequest;
import com.example.backend.user.RoleRepository;
import com.example.backend.user.UserRepository;
import com.example.backend.user.model.ERole;
import com.example.backend.user.model.Role;
import com.example.backend.wishlist.WishListRepository;
import com.example.backend.wishlist.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final GameRepository gameRepository;

    private final GameService gameService;

    private final WishListRepository wishListRepository;


    @Value("false")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            wishListRepository.deleteAll();
            gameRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            gameService.getDataFromApi();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("alex@email.com")
                    .username("alex")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("alex1@email.com")
                    .username("alex1")
                    .password("WooHoo1!")
                    .roles(Set.of("CUSTOMER"))
                    .build());
        }
    }
}
