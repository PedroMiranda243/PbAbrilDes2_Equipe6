package com.example.twitter.demo.security;

import com.example.twitter.demo.entity.Register;
import com.example.twitter.demo.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDatailsService implements UserDetailsService {

    @Autowired
    private RegisterService registerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Register register = registerService.buscarPorUsername(username);
        return new JwtUserDetails(register);
    }

    public JwtToken getTokenAuthenticated(String username) {
        Register.Role role = registerService.buscarRolePorUsername(username);
        return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
    }
}
