package in.gov.egs.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakClientProperties {

	private String tokenUri;
	private String redirectUri;
	private String logoutUri;
	private String postLogoutRedurect;
	private Map<String, ClientConfig> clients;

	public static class ClientConfig {
		private String clientId;
		private String clientSecret;

		public String getClientId() {
			return clientId;
		}

		public void setClientId(String clientId) {
			this.clientId = clientId;
		}

		public String getClientSecret() {
			return clientSecret;
		}

		public void setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
		}
	}

	public String getTokenUri() {
		return tokenUri;
	}

	public void setTokenUri(String tokenUri) {
		this.tokenUri = tokenUri;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public Map<String, ClientConfig> getClients() {
		return clients;
	}

	public void setClients(Map<String, ClientConfig> clients) {
		this.clients = clients;
	}

	public String getLogoutUri() {
		return logoutUri;
	}

	public void setLogoutUri(String logoutUri) {
		this.logoutUri = logoutUri;
	}

	public String getPostLogoutRedurect() {
		return postLogoutRedurect;
	}

	public void setPostLogoutRedurect(String postLogoutRedurect) {
		this.postLogoutRedurect = postLogoutRedurect;
	}
	
	
}
