package com.novelCheck.updatedbH2.repository;

import com.novelCheck.updatedbH2.model.UserUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserUserRepo extends CrudRepository<UserUser,Long> {
    List<UserUser> findByUserName(String username);

    UserUser findOneByUserName(String username);//powninno 1 znjadować
    //List<UserUser> findByUserName (String userName);



}
