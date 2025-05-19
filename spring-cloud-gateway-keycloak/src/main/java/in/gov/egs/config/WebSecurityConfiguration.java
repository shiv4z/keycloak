package in.gov.egs.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import in.gov.egs.pojo.RouteRole;
import in.gov.egs.service.RouteRoleService;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfiguration {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, RouteRoleService roleService) {

		ServerHttpSecurity.AuthorizeExchangeSpec exchange = http.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.authorizeExchange();

		exchange.pathMatchers("/eureka/**").permitAll();
		exchange.pathMatchers("/actuator/**").permitAll();
		exchange.pathMatchers("/health/**").permitAll();
		exchange.pathMatchers("/auth/**").permitAll();
		List<RouteRole> mappings = roleService.getRouteRoles();
		for (RouteRole rr : mappings) {
			exchange.pathMatchers(rr.getPath()).hasRole(rr.getRole());
		}

		exchange.anyExchange().authenticated();

		return http
				.oauth2ResourceServer(
						oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor())))
				.build();
	}

	private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
		JwtGrantedAuthoritiesConverter scopeConverter = new JwtGrantedAuthoritiesConverter();
		scopeConverter.setAuthorityPrefix("SCOPE_");

		return jwt -> {
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

			return Mono.just(new JwtAuthenticationToken(jwt, authorities));
		};
	}

}