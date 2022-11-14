package service.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import service.auth.model.ERole;
import service.auth.model.Role;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
