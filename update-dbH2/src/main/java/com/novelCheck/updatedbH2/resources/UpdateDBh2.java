package com.novelCheck.updatedbH2.resources;


import com.novelCheck.updatedbH2.model.*;
import com.novelCheck.updatedbH2.repository.KatalogUpdateRepo;
import com.novelCheck.updatedbH2.repository.UserUserRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

        @Autowired
        private SessionFactory sessionFactory;


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
            UserList userList = new UserList();
            userList.setUserUser((List<UserUser>)userUserRepo.findByUserName(username));
            return userList;//zwróciło inczaj niż wcześniej
        }

        @PostMapping(value = "/addUser",consumes = "application/json",produces = "application/json")
        public String addUser(@RequestBody UserUser userUser){
            userUserRepo.save(userUser);

            return "zaposano";//do testu
        }

        @PostMapping(value = "/subNoveltoUser",consumes = "application/json",produces = "application/json")
        public String subNoveltoUser(@RequestBody SubUserToNovelWrapper novelWrapper){

            UserUser userUser = userUserRepo.findOneByUserName(novelWrapper.getUserName());//powinno 1 zwracać
            System.out.println(userUser.getEmail());//test

            //TODO dodać wiadomość zwrotną jak nie znajdzie w db może opcja dopisywania do niej?
            KatalogUpdate katalogUpdate = katalogUpdateRepo.findOneByNovelID(novelWrapper.getNovelID());//powino 1 barc

            userUser.subToNovel(katalogUpdate);

            userUserRepo.save(userUser);

            return "zaposano";//do testu
        }
    @Transactional
        @PostMapping(value = "/delSubNovel",consumes = "application/json",produces = "application/json")
        public String delSubNovel(@RequestBody SubUserToNovelWrapper novelWrapper){//
            //SubUserToNovelWrapper novelWrapper = new SubUserToNovelWrapper("asd","the-second-coming-of-gluttony");

            //delSubNovelHelp(novelWrapper);
            UserUser userUser = userUserRepo.findOneByUserName(novelWrapper.getUserName());
            KatalogUpdate katalogUpdate = katalogUpdateRepo.findOneByNovelID(novelWrapper.getNovelID());

            Session session = sessionFactory.getCurrentSession();

            UserUser user = (UserUser)session.get(UserUser.class,userUser.getID());//złe id ma być long

            KatalogUpdate katalogUpdate1 = session.get(KatalogUpdate.class,katalogUpdate.getID());

            user.getSubNovel().remove(katalogUpdate1);

            session.update(user);
            return"asd";
        }

        private void delSubNovelHelp(SubUserToNovelWrapper novelWrapper){

        }
        @Transactional
        @GetMapping("/removeNovel")
        public void removeNovel(){
            Session session = sessionFactory.getCurrentSession();
            KatalogUpdate katalogUpdate = katalogUpdateRepo.findOneByNovelID("the-second-coming-of-gluttony");
            KatalogUpdate katalogUpdate1 = session.get(KatalogUpdate.class,katalogUpdate.getID());

            katalogUpdate1.zwróćSubskrybenci().forEach(user ->{
                user.getSubNovel().remove(katalogUpdate1);
            });
            session.delete(katalogUpdate1);//ty robisz bajzel

        }
        @PostMapping(value = "/updateUserMail")//zmienic na post
        public void updateUserMail(@RequestBody UserUser userUser){//

            UserUser userOld = userUserRepo.findOneByUserName(userUser.getUserName());
            userOld.setEmail(userUser.getEmail());

            userUserRepo.save(userOld);
        }
        @Transactional
        @GetMapping("/removeUser")
        public void removeUser(){
            Session session = sessionFactory.getCurrentSession();
            UserUser userUser = userUserRepo.findOneByUserName("asd");
            UserUser user = session.get(UserUser.class,userUser.getID());

            user.getSubNovel().forEach(novel ->{
                novel.zwróćSubskrybenci().remove(user);
            });
            session.delete(user);//ty robisz bajzel
        }

        @GetMapping("/mailGetAll")//TODO czy nie warto usunąć getAllUser????
        public UserList mailGetAll(){

            UserList userList = new UserList();
            userList.setUserUser((List<UserUser>)userUserRepo.findAll());
            return userList;//zwróciło inczaj niż wcześniej

        }
}

