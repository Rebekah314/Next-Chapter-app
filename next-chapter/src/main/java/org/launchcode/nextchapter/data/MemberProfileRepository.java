package org.launchcode.nextchapter.data;


import org.launchcode.nextchapter.models.MemberProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberProfileRepository extends CrudRepository<MemberProfile, Integer> {

}