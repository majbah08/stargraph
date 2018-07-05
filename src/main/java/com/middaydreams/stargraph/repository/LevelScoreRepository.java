package com.middaydreams.stargraph.repository;

import com.middaydreams.stargraph.model.LevelScore;
import com.middaydreams.stargraph.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import static org.hibernate.hql.internal.antlr.HqlTokenTypes.FROM;
import static org.hibernate.loader.Loader.SELECT;

@Repository
public interface LevelScoreRepository extends JpaRepository<LevelScore, Long> {
    LevelScore findAllByUseridAndModeAndCatagoryAndLevel(Users userid, String mode, String category, int level);

   @Query("SELECT max(p.level) FROM LevelScore p where p.userid = :user_id and p.mode = :mode and p.catagory=:catagory")
    int findHighestLevel(@Param("user_id") Users user_id, @Param("mode") String mode, @Param("catagory")String catagory);

   // LevelScore findFirstByLevelAndUseridAndModeAndCatagoryOrderByLevelDesc(Users userid, String mode, String category);
}