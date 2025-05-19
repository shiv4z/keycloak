docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev


docker run --name keycloak_app -p 8080:8080 -e KC_DB=postgres -e KC_DB_URL=jdbc:postgresql://host.docker.internal:5432/keycloakdb -e KC_DB_USERNAME=keycloakuser -e KC_DB_PASSWORD=yourpassword -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:24.0.1 start-dev
docker run --name keycloak_app -p 8080:8080 -e KC_DB=postgres -e KC_DB_URL=jdbc:postgresql://host.docker.internal:5432/keycloakdb -e KC_DB_USERNAME=keycloakuser -e KC_DB_PASSWORD=yourpassword -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev


docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -e KC_DB=postgres -e KC_DB_URL=jdbc:postgresql://host.docker.internal:5432/keycloakdb -e KC_DB_USERNAME=keycloakuser -e KC_DB_PASSWORD=yourpassword quay.io/keycloak/keycloak:latest start-dev

http://localhost:8080/realms/rest-api/protocol/openid-connect/auth?client_id=keycloak-rest&response_type=code&scope=openid&redirect_uri=http://localhost:9090/auth/callback

http://localhost:8080/realms/rest-api/protocol/openid-connect/auth?client_id=keycloak-rest&response_type=code&scope=openid&redirect_uri=http://localhost:9090/auth/callback&state=client-a

http://localhost:8080/realms/rest-api/protocol/openid-connect/auth?client_id=keycloak-rest-app&response_type=code&scope=openid&redirect_uri=http://localhost:9090/auth/callback&state=client-b

CREATE TABLE route_role_mappings (
    id SERIAL PRIMARY KEY,
    path VARCHAR(255) NOT NULL,
    role VARCHAR(100) NOT NULL
);


insert into route_role_mappings  values (1, '/rest-api/products/**', 'admin');
insert into route_role_mappings  values (2, '/rest-api/prechecker/**', 'prechecker');
insert into route_role_mappings  values (3, '/rest-api/fruits/**', 'user');
insert into route_role_mappings  values (4, '/rest-api/actors/**', 'user');
insert into route_role_mappings  values (5, '/rest-api/states/**', 'maker');


select * from route_role_mappings rrm ;




CREATE DATABASE keycloakdb;
CREATE USER keycloakuser WITH PASSWORD 'yourpassword';
GRANT ALL PRIVILEGES ON DATABASE keycloakdb TO keycloakuser;

-- Replace `keycloak_user` with the actual user you’re using for Keycloak
GRANT ALL PRIVILEGES ON SCHEMA public TO keycloakuser;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO keycloakuser;

ALTER SCHEMA public OWNER TO keycloakuser;


ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO keycloakuser;

GRANT USAGE ON SCHEMA public TO keycloakuser;
GRANT CREATE ON SCHEMA public TO keycloakuser;

