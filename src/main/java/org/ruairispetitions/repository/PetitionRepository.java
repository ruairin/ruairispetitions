package org.ruairispetitions.repository;

import java.util.List;

import org.ruairispetitions.model.Petition;
import org.springframework.data.repository.ListCrudRepository;

public interface PetitionRepository extends ListCrudRepository<Petition, Integer> {
    List<Petition> findAllByTitleIgnoreCaseContains(String title);
}
