package com.facugl.ecommerce.server.application.service.security;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.facugl.ecommerce.server.application.port.input.security.auth.FindLoggedInUserUseCase;
import com.facugl.ecommerce.server.application.port.input.security.auth.GenerateTokenUseCase;
import com.facugl.ecommerce.server.application.port.input.security.auth.LoginUseCase;
import com.facugl.ecommerce.server.application.port.input.security.auth.LogoutUseCase;
import com.facugl.ecommerce.server.application.port.input.security.auth.RegisterUserUseCase;
import com.facugl.ecommerce.server.application.service.CustomUserDetailsService;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.security.JwtToken;
import com.facugl.ecommerce.server.domain.model.security.User;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security.AuthenticationRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.AuthenticationResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.LoggedInUserResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.UserResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.UserResponse.UserResponseBuilder;
import com.facugl.ecommerce.server.infrastructure.adapter.security.user.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class AuthenticationService implements
		RegisterUserUseCase,
		LoginUseCase,
		LogoutUseCase,
		FindLoggedInUserUseCase {
	private final UserService userService;
	private final CustomUserDetailsService customUserDetailsService;
	private final JwtService jwtService;
	private final GenerateTokenUseCase generateTokenUseCase;
	private final AuthenticationManager authenticationManager;

	@Transactional
	@Override
	public UserResponse registerOneCustomer(User userToCreate) {
		User user = userService.createUser(userToCreate);
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		String jwt = generateTokenUseCase.generateToken(customUserDetails, generateExtraClaims(customUserDetails));

		saveUserToken(customUserDetails, jwt);

		UserResponseBuilder userResponse = UserResponse.builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.username(user.getUsername())
				.email(user.getEmail())
				.phoneNumber(user.getPhoneNumber())
				.role(user.getRole().getName());

		userResponse.jwt(jwt);

		return userResponse.build();
	}

	private void saveUserToken(CustomUserDetails customUserDetails, String jwt) {
		JwtToken jwtToken = JwtToken.builder()
				.token(jwt)
				.userId(customUserDetails.getUser().getId())
				.expiration(jwtService.extractExpiration(jwt))
				.isValid(true)
				.build();

		jwtService.saveJwtToken(jwtToken);
	}

	private Claims generateExtraClaims(CustomUserDetails customUserDetails) {
		Claims extraClaims = Jwts.claims()
				.add("name", customUserDetails.getName())
				.add("role", customUserDetails.getRole())
				.add("authorities", customUserDetails.getAuthorities())
				.build();

		return extraClaims;
	}

	@Transactional
	@Override
	public AuthenticationResponse login(AuthenticationRequest authRequest) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				authRequest.getUsername(), authRequest.getPassword());

		authenticationManager.authenticate(authentication);

		CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService
				.loadUserByUsername(authRequest.getUsername());

		String jwt = generateTokenUseCase.generateToken(customUserDetails, generateExtraClaims(customUserDetails));

		saveUserToken(customUserDetails, jwt);

		AuthenticationResponse authResponse = new AuthenticationResponse();
		authResponse.setJwt(jwt);

		return authResponse;
	}

	@Override
	public LoggedInUserResponse findLoggedInUser() {
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder
				.getContext().getAuthentication();

		String username = (String) auth.getPrincipal();
		User user = userService.getUserByUsername(username);

		LoggedInUserResponse LoggedInUser = LoggedInUserResponse.builder()
				.id(user.getId())
				.name(user.getFirstName() + " " + user.getLastName())
				.username(username)
				.email(user.getEmail())
				.role(user.getRole().getName())
				.authorities(auth.getAuthorities().stream()
						.map(authority -> authority.getAuthority())
						.collect(Collectors.toList()))
				.build();

		return LoggedInUser;
	}

	@Override
	public void logout(HttpServletRequest request) {
		String jwt = jwtService.extractJwtFromRequest(request);

		if (jwt == null || !StringUtils.hasText(jwt))
			return;

		Optional<JwtToken> jwtToken = jwtService.findJwtTokenByToken(jwt);

		if (jwtToken.isPresent() && jwtToken.get().isValid()) {
			jwtToken.get().setValid(false);

			jwtService.saveJwtToken(jwtToken.get());
		}
	}
}
