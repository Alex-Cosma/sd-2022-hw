package com.rdaniel.sd.a2.backend.user;

import com.rdaniel.sd.a2.backend.user.dto.UserDetailsImpl;
import com.rdaniel.sd.a2.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .map(UserDetailsImpl::build)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
  }
}
