package com.middaydreams.stargraph.repository;


import com.middaydreams.stargraph.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Majbah on 26/06/20018.
 * NeverMoreGG.oO
 */

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByFbid(String fbid);
    Users findByUserid(String userid);

}
