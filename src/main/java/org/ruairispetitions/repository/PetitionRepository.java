package org.ruairispetitions.repository;

import org.ruairispetitions.model.Petition;
import org.springframework.data.repository.ListCrudRepository;

public interface PetitionRepository extends ListCrudRepository<Petition, Integer> {
    
}
