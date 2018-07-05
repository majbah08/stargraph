package com.middaydreams.stargraph.repository;

import com.middaydreams.stargraph.model.Totalscore;
import com.middaydreams.stargraph.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalScoreRepository extends JpaRepository<Totalscore, Long> {
    Totalscore findAllByUserid(Users user);
}
