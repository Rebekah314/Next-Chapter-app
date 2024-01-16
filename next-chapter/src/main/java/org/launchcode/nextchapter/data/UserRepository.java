package org.launchcode.nextchapter.data;


import org.launchcode.nextchapter.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
