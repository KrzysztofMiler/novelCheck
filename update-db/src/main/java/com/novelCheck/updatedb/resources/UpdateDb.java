package com.novelCheck.updatedb.resources;

import com.novelCheck.updatedb.models.KatalogUpdate;
import com.novelCheck.updatedb.models.UserKatalog;
import com.novelCheck.updatedb.repository.NovelRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/db")
public class UpdateDb {

    private NovelRepo novelRepo;

    @GetMapping("/{userID}")
    public UserKatalog getUserKatalog(@PathVariable("userID") String userID){
        novelRepo.findByUserName(userID).stream()
                .map(novel ->{
                    return new UserKatalog(novel.getNovelID(),novel.getStrona());
                }).collect(Collectors.toList());



        /*List<KatalogUpdate> katalog = Arrays.asList(
                new KatalogUpdate("the-second-coming-of-gluttony","NovelUpdates"),
                new KatalogUpdate("second-life-ranker","NovelUpdates"),
                new KatalogUpdate("overgeared","NovelUpdates"),
                new KatalogUpdate("41306/he-who-fights-with-monsters", "ScribbleHub")
        );*/
        UserKatalog userKatalog = new UserKatalog();
        userKatalog.setNovels((List<KatalogUpdate>) novelRepo);
        return userKatalog;//
    }
}
