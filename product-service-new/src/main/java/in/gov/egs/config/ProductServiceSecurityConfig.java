package in.gov.egs.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import in.gov.egs.pojo.RouteRole;
import in.gov.egs.service.RouteRoleService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ProductServiceSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, RouteRoleService roleService) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> {
			auth.requestMatchers("/eureka/**", "/actuator/**", "/health/**").permitAll();

			List<RouteRole> mappings = roleService.getRouteRoles();
			for (RouteRole rr : mappings) {
				auth.requestMatchers(rr.getPath()).hasRole(rr.getRole());
			}

			auth.anyRequest().authenticated();
		}).oauth2ResourceServer(
				oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor())));

		return http.build();
	}

	@Bean
	public JwtAuthenticationConverter grantedAuthoritiesExtractor() {
		JwtGrantedAuthoritiesConverter scopeConverter = new JwtGrantedAuthoritiesConverter();
		scopeConverter.setAuthorityPrefix("SCOPE_");

		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(jwt -> {
			List<GrantedAuthority> authorities = new ArrayList<>(scopeConverter.convert(jwt));

			Map<String, Object> realmAccess = jwt.getClaim("realm_access");
			if (realmAccess != null && realmAccess.containsKey("roles")) {
				Object rolesObj = realmAccess.get("roles");
				if (rolesObj instanceof List<?>) {
					for (Object roleObj : (List<?>) rolesObj) {
						if (roleObj instanceof String role) {
							authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
						}
					}
				}
			}

			String employeeType = jwt.getClaimAsString("employeeType");
			if (employeeType != null && !employeeType.isEmpty()) {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + employeeType));
			}

			return authorities;
		});

		return converter;
	}
}