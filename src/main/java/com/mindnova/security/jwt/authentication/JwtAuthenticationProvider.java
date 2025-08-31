package com.mindnova.security.jwt.authentication;

import com.mindnova.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        boolean isValidToken  = tokenProvider.validateToken(token);
        if (!isValidToken)
            return null;
        Map<String, Object> payload = tokenProvider.getPayload(token);
        Integer userId = (Integer) payload.get("userId");
        String principle = payload.get("email").toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(principle);
        return new JwtAuthenticationToken(userDetails.getAuthorities(), principle, token,userId);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
