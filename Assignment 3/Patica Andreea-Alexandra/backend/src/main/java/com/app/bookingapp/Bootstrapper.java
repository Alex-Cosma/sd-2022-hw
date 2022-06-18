//package com.app.bookingapp;
//
//import project.booking.item.GenreRepository;
//import project.booking.item.model.EGenre;
//import project.booking.item.model.Genre;
//import project.booking.user.RoleRepository;
//import project.booking.user.UserRepository;
//import project.booking.user.model.ERole;
//import project.booking.user.model.Role;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {
//
//    private final RoleRepository roleRepository;
//
//    private final UserRepository userRepository;
//
//    private final GenreRepository genreRepository;
//
//    @Value("${app.bootstrap}")
//    private Boolean bootstrap;
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//        if(bootstrap) {
//            userRepository.deleteAll();
//            roleRepository.deleteAll();
//            genreRepository.deleteAll();
//            for (ERole value : ERole.values()) {
//                roleRepository.save(
//                        Role.builder()
//                                .name(value)
//                                .build()
//                );
//            }
//            for (EGenre value : EGenre.values()) {
//                genreRepository.save(
//                        Genre.builder()
//                                .name(value)
//                                .build()
//                );
//            }
//        }
//    }
//}
