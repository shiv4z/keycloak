package in.gov.egs.keycloak;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;

import java.util.List;

public class CustomRoleAuthenticator implements Authenticator {

	public void authenticate(AuthenticationFlowContext context) {
		String username = context.getUser().getUsername();

		// Fetch roles from your DB using JDBC/JPA or any service
		List<String> rolesFromDb = fetchRolesFromDatabase(username);

		KeycloakSession session = context.getSession();
		RealmModel realm = context.getRealm();

		for (String roleName : rolesFromDb) {
			RoleModel role = realm.getRole(roleName);
			if (role == null) {
				role = realm.addRole(roleName); // You can remove this if roles must already exist
			}

			// Grant role to the user
			context.getUser().grantRole(role);

			// Ensure the role is included in the token by adding it to realm_access
		//	context.getClientSession().setAttribute("realm_access", roleName);
		}

		context.success();
	}

	private List<String> fetchRolesFromDatabase(String username) {
		// Replace this dummy data with actual DB access (JPA, JDBC, etc.)
		return List.of("user", "admin");
	}

	@Override
	public void action(AuthenticationFlowContext context) {
		// Not used for this type of authenticator
	}

	@Override
	public boolean requiresUser() {
		return true; // true because we're granting roles to the authenticated user
	}

	@Override
	public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
		return true; // Always run
	}

	@Override
	public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
		// No additional actions required
	}

	@Override
	public void close() {
		// Clean up resources if needed
	}
}