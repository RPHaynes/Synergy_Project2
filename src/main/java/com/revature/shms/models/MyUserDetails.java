package com.revature.shms.models;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetails implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private Role role;

    public MyUserDetails(){

    }

    //Override UserDetails to be our users
    public MyUserDetails(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        //this.role = role;
        //this.authorities = Arrays.stream(user.getRoles().split(","))
        //        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return authorities;
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        //change this
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //change this
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //change this
        return true;
    }

    @Override
    public boolean isEnabled() {
        //change this
        return true;
    }

}