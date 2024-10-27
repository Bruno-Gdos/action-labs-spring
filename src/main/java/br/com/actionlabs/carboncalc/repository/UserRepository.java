package br.com.actionlabs.carboncalc.repository;

import br.com.actionlabs.carboncalc.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
