package com.novelCheck.updatedbH2.resources;


import com.novelCheck.updatedbH2.model.KatalogUpdate;
import com.novelCheck.updatedbH2.model.UserKatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/db")
public class UpdateDBh2 {

        @Autowired
        private CrudRepository repository;


        @GetMapping("/getAll")
        public UserKatalog getAll(){
            //repository.findAll();
            UserKatalog userKatalog = new UserKatalog();
            userKatalog.setNovels((List<KatalogUpdate>) repository.findAll());
            return userKatalog;
        }
        @PostMapping("/saveNovel")
        public String saveNovel(@RequestBody KatalogUpdate katalogUpdate){
            repository.save(katalogUpdate);
            return "zaposano";//do testu
        }
//        @GetMapping("/getNovel/{novelID}")
 //       public List<KatalogUpdate> getNovel(@PathVariable("novelID") String novelID){
  //          return repository.findBynovelID(novelID);
   //     }
        //public void demo (DbRepo repo){
//            return (args -> {
//                repo.save(new KatalogUpdate("the-second-coming-of-gluttony","NovelUpdates"));
//                repo.save(new KatalogUpdate("the-second-coming-of-gluttony","NovelUpdates"));
//                repo.save(new KatalogUpdate("the-second-coming-of-gluttony","NovelUpdates"));
//                repo.save(new KatalogUpdate("the-second-coming-of-gluttony","NovelUpdates"));
//            })
        //}
}

