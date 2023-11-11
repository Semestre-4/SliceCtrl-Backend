package com.mensal.slicectrl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario",schema = "public")
@Getter
@Setter
public class Usuario extends AbstractEntity implements UserDetails {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "password", nullable = false,unique = true)
    private String password;

    @Column(name = "telefone", nullable = false, unique = true)
    private String telefone;

    @Column(name = "salario")
    private BigDecimal salario;

    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pedidos> pedidos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}