package in.gov.egs.keycloak;

import java.util.List;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.protocol.ProtocolMapper;
import org.keycloak.provider.ProviderConfigProperty;
import org.springframework.stereotype.Component;

public class CustomRoleProtocolMapperFactory implements ProtocolMapper {
	public static final String PROVIDER_ID = "custom-role-protocol-mapper";

	@Override
	public String getId() {
		return PROVIDER_ID;
	}

	@Override
	public ProtocolMapper create(KeycloakSession session) {
		return new CustomRoleProtocolMapper();
	}

	@Override
	public String getDisplayType() {
		return "Custom Role Protocol Mapper";
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(Scope arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postInit(KeycloakSessionFactory arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ProviderConfigProperty> getConfigProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHelpText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

}
