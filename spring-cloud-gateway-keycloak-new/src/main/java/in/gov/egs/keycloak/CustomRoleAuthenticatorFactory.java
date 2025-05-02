package in.gov.egs.keycloak;


import java.util.List;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;



public class CustomRoleAuthenticatorFactory implements ConfigurableAuthenticatorFactory {

    public static final String PROVIDER_ID = "custom-role-authenticator";

    public String getId() {
        return PROVIDER_ID;
    }

    public Authenticator create(KeycloakSession session) {
        return new CustomRoleAuthenticator(); 
    }

    @Override
    public String getDisplayType() {
        return "Custom Role Authenticator";
    }

    @Override
    public String getHelpText() {
        return "Fetch roles from DB after LDAP login";
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return List.of();
    }

    public Requirement[] getRequirementChoices() {
        return new Requirement[]{Requirement.REQUIRED};
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    public void init(Config.Scope config) {
        // optional
    }

    public void postInit(KeycloakSessionFactory factory) {
        // optional
    }

    public void close() {
        // optional
    }

	@Override
	public String getReferenceCategory() {
		// TODO Auto-generated method stub
		return null;
	}
}