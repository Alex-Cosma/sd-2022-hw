package com.lab4.demo;

import com.lab4.demo.payment.PaymentRepository;
import com.lab4.demo.playlist.PlaylistRepository;
import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.track.TrackRepository;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;


    private final PlaylistRepository playlistRepository;

    private final TrackRepository trackRepository;

    private final PaymentRepository paymentRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            userRepository.deleteAll();
            roleRepository.deleteAll();
            playlistRepository.deleteAll();
            paymentRepository.deleteAll();
            //trackRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("alex@gmail.com")
                    .username("alex")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("sandordoroteea@gmail.com")
                    .username("alex1")
                    .password("WooHoo1!")
                    .roles(Set.of("EMPLOYEE"))
                    .build());
//            authService.register(SignupRequest.builder()
//                    .email("sandordoroteea@gmail.com")
//                    .username("doro")
//                    .password("WooHoo1!")
//                    .roles(Set.of("EMPLOYEE"))
//                    .build());

//            trackRepository.save(Track.builder()
//                    .title("title song1")
//                    .link("link1")
//                    .preview("preview1")
//                    .duration(122)
//                    .explicit_lyrics(true)
//                    .artist("name1")
//                    .album("title album1")
//                    .build());
//
//            trackRepository.save(Track.builder()
//                    .title("title song2")
//                    .link("link2")
//                    .preview("preview1")
//                    .duration(122)
//                    .explicit_lyrics(true)
//                    .artist("name1")
//                    .album("title album1")
//                    .build());

//            List<Track> tracks = trackRepository.findAll();
//            playlistRepository.save(Playlist.builder()
//                    .title("My Playlist")
//                    .tracks(tracks)
//                    .duration(100)
//                    .build());
//
        }
    }
}