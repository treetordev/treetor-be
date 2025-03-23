package com.example.treetor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Entity
@Table(name = "users")
@NoArgsConstructor
public class UserModel implements UserDetails {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String name;
	private String email;
	private String password;
	private String about;
	private String roles;
	private String userKey;
	private String userDetailsId;

	@JsonIgnore
	@OneToOne(mappedBy = "user")
	private RefreshToken refreshToken;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.stream(roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}