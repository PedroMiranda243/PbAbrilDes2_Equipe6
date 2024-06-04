package com.example.twitter.demo.security;

import com.example.twitter.demo.entity.Register;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtUserDetails extends org.springframework.security.core.userdetails.User {

    private Register register;

    public JwtUserDetails(Register register) {
        super(register.getUsername(), register.getPassword(), AuthorityUtils.createAuthorityList(register.getRole().name()));
        this.register = register;
    }

    public Long getId(){
        return this.register.getId();
    }

    public String getRole(){
        return this.register.getRole().name();
    }

}
