package com.wissen.tpu.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

	private final String code;
	private final String department;
	private final Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(String code, String department, Collection<? extends GrantedAuthority> authorities) {
		this.code = code;
		this.department = department;
		this.authorities = authorities;
	}

	public String getDepartment() {
		return department;
	}

	public boolean hasRole(String role) {
		return authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return code;
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
