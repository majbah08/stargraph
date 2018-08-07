package com.middaydreams.stargraph.controller;

import com.middaydreams.stargraph.exception.ResourceNotFoundException;

import com.middaydreams.stargraph.model.LevelScore;
import com.middaydreams.stargraph.model.Totalscore;
import com.middaydreams.stargraph.model.Users;
import com.middaydreams.stargraph.repository.LevelScoreRepository;
import com.middaydreams.stargraph.repository.TotalScoreRepository;
import com.middaydreams.stargraph.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Random;

/**
 * Created by Majbah on 26/06/20018.
 * NeverMoreGG.oO
 */
@RestController
@RequestMapping("/api")
public class StarGarphController {

    @Autowired
    UserRepository userszRepository;

    @Autowired
    LevelScoreRepository levelScoreRepository;

    @Autowired
    TotalScoreRepository totalScoreRepository;



    @RequestMapping("/")
    public String index() {
        return "<h1><font color=red>Hello World From StarGraph Game</font></h1>";
    }


    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return userszRepository.findAll();
    }

    @PostMapping("/users")
    public Users createUsers(@Valid @RequestBody Users users) {

        return userszRepository.save(users);
    }


    @PostMapping("/register/{fbid}" )
    public Users registerUsers(@PathVariable String fbid) {


        char[] chars = "0123456789".toCharArray();
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(( rnd.nextInt(900000)) );
        for (int i = 0; i < 5; i++)
            sb.append(chars[rnd.nextInt(chars.length)]);

        String uId=sb.toString();
        Users users=new Users();
        users.setUserid(uId);
        users.setFbid(fbid);
        return userszRepository.save(users);
    }

    @GetMapping("/checkUser/{fbsid}")
    public Users getUsersByFbId(@PathVariable String fbsid ) {
        Object fbid=fbsid;
        return userszRepository.findByFbid(fbsid);
            //   .orElseThrow(() -> {
              //     return new ResourceNotFoundException("Users", "id", fbid);
              // });
    }





    @GetMapping("/users/{id}")
    public Users getUsersById(@PathVariable(value = "id") Long userid) {
        return userszRepository.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "id", userid));
    }

    @PutMapping("/users/{id}")
    public Users updateUser(@PathVariable(value = "id") Long userid,
                                           @Valid @RequestBody Users userDetails) {

        Users user = userszRepository.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "id", userid));

        user.setUserid(userDetails.getUserid());
        user.setFbid(userDetails.getFbid());

        Users updatedUser = userszRepository.save(user);
        return updatedUser;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
        Users user = userszRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "id", userId));

        userszRepository.delete(user);

        return ResponseEntity.ok().build();
    }

 /*
   ########### Level data code Starts here ##########
  */


    @PostMapping("/levelscore/{userid}" )
    public LevelScore setLevelScore(@PathVariable String userid,@RequestParam String mode,@RequestParam String category, @RequestParam int level, @RequestParam int xp,@RequestParam int scores) {

        Users user =userszRepository.findByUserid(userid);
        LevelScore ls= levelScoreRepository.findAllByUseridAndModeAndCatagoryAndLevel(user,mode,category,level);

        LevelScore score=new LevelScore();
        user.setUserid(user.getUserid());
        score.setUserid(user);
        score.setMode(mode);
        score.setCatagory(category);
        score.setLevel(level);
        score.setXp(xp);
        score.setScore(scores);
        if(ls==null ) {
            return levelScoreRepository.save(score);
        }else return levelScoreRepository.update(scores,xp,mode,category,level);


    }


    @GetMapping("/levelscore/{userid}" )
    public LevelScore getLevelScoreByUserId(@PathVariable String userid,@RequestParam String mode,@RequestParam String category, @RequestParam int level) {

        Users user =userszRepository.findByUserid(userid);



        return levelScoreRepository.findAllByUseridAndModeAndCatagoryAndLevel(user,mode,category,level);



    }



    @GetMapping("/toplevel/{userid}" )
    public int getTopLEvelCatagoryByUserId(@PathVariable String userid,@RequestParam String mode,@RequestParam String category) {

        Users user =userszRepository.findByUserid(userid);



        return levelScoreRepository.findHighestLevel(user,mode,category);




    }


 /*
    ########### Level data code Ends here ##########
  */



         /*
    ########### Total score data code Starts here ##########
  */



    @PostMapping("/totalscore/{userid}" )
    public Totalscore setLevelScore(@PathVariable String userid, @RequestParam int xp,@RequestParam int scores) {

        Users user =userszRepository.findByUserid(userid);

        Totalscore totalscore=new Totalscore();
       totalscore.setUserid(user);
       totalscore.setTotalScore(scores);
       totalscore.setTotalXp(xp);

        return totalScoreRepository.save(totalscore) ;
    }


    @GetMapping("/totalscore/{userid}" )
    public Totalscore getTotalLevelScoreByUserId(@PathVariable String userid) {

        Users user =userszRepository.findByUserid(userid);



        return totalScoreRepository.findAllByUserid(user);



    }

         /*
    ########### Total score data code Starts here ##########
  */




}
