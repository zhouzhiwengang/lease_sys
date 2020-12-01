package com.zzg.shrio;

import org.apache.shiro.authc.AuthenticationToken;

public class JWTToken implements AuthenticationToken{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6747540462673342320L;
	private String token;

    public JWTToken(String token) {
        this.token = token;
    }


    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
