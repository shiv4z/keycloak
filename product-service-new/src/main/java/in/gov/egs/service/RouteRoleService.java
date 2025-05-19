package in.gov.egs.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import in.gov.egs.pojo.RouteRole;
import in.gov.egs.repo.RouteRoleMappingRepository;

@Service
public class RouteRoleService {

    private final RouteRoleMappingRepository repository;

    public RouteRoleService(RouteRoleMappingRepository repository) {
        this.repository = repository;
    }

    public List<RouteRole> getRouteRoles() {
        return repository.findAll().stream()
                .map(entity -> new RouteRole(entity.getPath(), entity.getRole()))
                .collect(Collectors.toList());
    }
}
