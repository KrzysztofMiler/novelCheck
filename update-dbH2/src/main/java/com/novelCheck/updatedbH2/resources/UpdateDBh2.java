package com.novelCheck.updatedbH2.resources;


import com.novelCheck.updatedbH2.model.KatalogUpdate;
import com.novelCheck.updatedbH2.model.UserKatalog;
import com.novelCheck.updatedbH2.model.UserList;
import com.novelCheck.updatedbH2.model.UserUser;
import com.novelCheck.updatedbH2.repository.KatalogUpdateRepo;
import com.novelCheck.updatedbH2.repository.UserUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
//TODO zobaczyć czy @Service ma sens
@RestController
@RequestMapping("/db")
public class UpdateDBh2 {

        @Autowired
        private KatalogUpdateRepo katalogUpdateRepo;

        @Autowired
        private UserUserRepo userUserRepo;

        @GetMapping("/getAll")
        public UserKatalog getAll(){
            //repository.findAll();
            UserKatalog userKatalog = new UserKatalog();
            userKatalog.setNovels((List<KatalogUpdate>) katalogUpdateRepo.findAll());
            return userKatalog;
        }
        @PostMapping(value = "/saveNovel",consumes = "application/json",produces = "application/json")
        public String saveNovel(@RequestBody KatalogUpdate katalogUpdate){
            katalogUpdateRepo.save(katalogUpdate);
            return "zaposano";//do testu
        }
        @GetMapping("/getNovel/{novelID}")
        public UserKatalog getNovel(@PathVariable("novelID") String novelID){
            UserKatalog userKatalog = new UserKatalog();
            userKatalog.setNovels(katalogUpdateRepo.findByNovelID(novelID));//tutaj nie można zcastować (List<KatalogUpdate>)
            return userKatalog;
        }
        @GetMapping("/getStrona/{strona}")
        public UserKatalog getStrona(@PathVariable("strona") String strona){
            UserKatalog userKatalog = new UserKatalog();//działa ale głupio
            userKatalog.setNovels(katalogUpdateRepo.findByStrona(strona));
            return userKatalog;
        }

        @GetMapping("/getAllUsers")
        public List<UserUser> getAllUsers(){
            List<UserUser> userUser = new ArrayList<UserUser>();
            userUserRepo.findAll().forEach(user -> userUser.add(user));//trochę bardziej mi się podoba
            return userUser;//działa ale zwraca lisę obj może będą z tym poblemy
        }
        @GetMapping("/getUser/{username}")
        public List<UserUser> getUser(@PathVariable("username") String username){
            List<UserUser> userUser = new ArrayList<UserUser>();
            userUserRepo.findByUserName(username).forEach(user -> userUser.add(new UserUser(user.getUserName(),user.getEmail())));
            return userUser;//zwraca usrname+ email+ puste subnovel -  id null -  ale jesst jako objekt więc mogę zignorować narazie
        }
        @GetMapping("/getUserNovel/{username}")
        public UserList getUserNovel(@PathVariable("username") String username){
            List<UserUser> userUser = new ArrayList<UserUser>();

            UserList userList = new UserList();
            userList.setUserUser( userUserRepo.findByUserName(username));
            return userList;
        }
}

