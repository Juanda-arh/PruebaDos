package com.btg.funds.infrastructure.security;

import com.btg.funds.domain.model.Cliente;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ClientePrincipal implements UserDetails {
    private final String id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public ClientePrincipal(Cliente cliente) {
        this.id = cliente.getId();
        this.email = cliente.getEmail();
        this.password = cliente.getPassword();
        this.authorities = cliente.getRoles() == null ? List.of()
                : cliente.getRoles().stream().map(SimpleGrantedAuthority::new).toList();
    }

    public String getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
