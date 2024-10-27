package br.com.actionlabs.carboncalc.repository;
import br.com.actionlabs.carboncalc.model.Calculation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CalculationRepository extends MongoRepository<Calculation, String> {
    List<Calculation> findByUserId(String userId);
}
