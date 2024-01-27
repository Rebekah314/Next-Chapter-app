package org.launchcode.nextchapter.data;


import org.launchcode.nextchapter.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username); //method signature creates SQL query

}
