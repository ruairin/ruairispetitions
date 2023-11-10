/**
 * The Spring Data repository for the petitions application
 * 
 * @author ruairin
 *
 */

package org.ruairispetitions.repository;

import java.util.List;

import org.ruairispetitions.model.Petition;
import org.springframework.data.repository.ListCrudRepository;

public interface PetitionRepository extends ListCrudRepository<Petition, Integer> {
    // Additional method for finding peitions whose title contains the search term
    List<Petition> findAllByTitleIgnoreCaseContains(String searchString);
}
