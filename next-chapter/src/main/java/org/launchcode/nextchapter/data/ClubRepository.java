package org.launchcode.nextchapter.data;

import org.launchcode.nextchapter.models.Club;
import org.launchcode.nextchapter.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends CrudRepository<Club, Integer> {

    Club findByDisplayName(String displayName); //method signature creates SQL query


}
