package in.gov.egs.keycloak;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAccessTokenMapper;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.AccessToken;

public class CustomRoleProtocolMapper extends AbstractOIDCProtocolMapper implements OIDCAccessTokenMapper {

    public static final String PROVIDER_ID = "custom-role-protocol-mapper";

    @Override
    public AccessToken transformAccessToken(AccessToken token, ProtocolMapperModel mappingModel,
                                            KeycloakSession session, UserSessionModel userSession,
                                            ClientSessionContext clientSessionCtx) {
        Set<String> roles = userSession.getUser().getRoleMappingsStream()
            .map(RoleModel::getName)
            .collect(Collectors.toSet());

        if (token.getRealmAccess() == null) {
            token.setRealmAccess(new AccessToken.Access());
        }

        token.getRealmAccess().roles(roles);
        return token;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Custom Role Protocol Mapper";
    }

    @Override
    public String getHelpText() {
        return "Adds dynamic user roles into access token";
    }

    public List<ProviderConfigProperty> getConfigProperties() {
        return List.of();
    }

    @Override
    public String getDisplayCategory() {
        return TOKEN_MAPPER_CATEGORY;
    }

    public AccessToken transformIDToken(AccessToken token, ProtocolMapperModel mappingModel, KeycloakSession session,
                                        UserSessionModel userSession, ClientSessionContext clientSessionCtx) {
        return token;
    }

    @Override
    public AccessToken transformUserInfoToken(AccessToken token, ProtocolMapperModel mappingModel, KeycloakSession session,
                                              UserSessionModel userSession, ClientSessionContext clientSessionCtx) {
        return token;
    }
}