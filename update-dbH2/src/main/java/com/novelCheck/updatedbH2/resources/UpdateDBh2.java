package com.novelCheck.updatedbH2.resources;


import com.novelCheck.updatedbH2.model.KatalogUpdate;
import com.novelCheck.updatedbH2.model.UserKatalog;
import com.novelCheck.updatedbH2.repository.DbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/db")
public class UpdateDBh2 {

        @Autowired
        private DbRepo repository;

        @GetMapping("/getAll")
        public UserKatalog getAll(){
            //repository.findAll();
            UserKatalog userKatalog = new UserKatalog();
            userKatalog.setNovels((List<KatalogUpdate>)repository.findAll());
            return userKatalog;
        }
        @PostMapping("/saveNovel")
        public String saveNovel(@RequestBody KatalogUpdate katalogUpdate){
            repository.save(katalogUpdate);
            return "zaposano";//do testu
        }
        @GetMapping("/getNovel/{novelID}")
        public UserKatalog getNovel(@PathVariable("novelID") String novelID){
            UserKatalog userKatalog = new UserKatalog();
            userKatalog.setNovels(repository.findByNovelID(novelID));//tutaj nie można zcastować (List<KatalogUpdate>)
            return userKatalog;
        }
    @GetMapping("/getStrona/{strona}")
        public UserKatalog getStrona(@PathVariable("strona") String strona){
        UserKatalog userKatalog = new UserKatalog();//działa ale głupio
        userKatalog.setNovels(repository.findByStrona(strona));
        return userKatalog;
    }
}

