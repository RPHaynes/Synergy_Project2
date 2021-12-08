package com.revature.shms.models;
import com.revature.shms.enums.Roles;
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
public class secUserDetails implements UserDetails {



    public secUserDetails(){

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return authorities;
        return Arrays.asList(new SimpleGrantedAuthority(Roles.USER.getValue()));
    }

    @Override
    public String getPassword() {
        return "password";
    }

    @Override
    public String getUsername() {
        return "username";
    }


	public Roles getRole(){
		return Roles.USER;
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