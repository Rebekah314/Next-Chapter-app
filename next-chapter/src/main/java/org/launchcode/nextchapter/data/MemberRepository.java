package org.launchcode.nextchapter.data;


import org.launchcode.nextchapter.models.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, Integer> {

    Member findByUsername(String username); //method signature creates SQL query

}
