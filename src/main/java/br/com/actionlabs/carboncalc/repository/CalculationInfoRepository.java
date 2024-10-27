package br.com.actionlabs.carboncalc.repository;

import br.com.actionlabs.carboncalc.model.CalculationInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CalculationInfoRepository extends MongoRepository<CalculationInfo, String> {
    List<CalculationInfo> findByUserId(String userId);
}
