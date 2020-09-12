package com.novelCheck.updateList.resources;


import com.novelCheck.updateList.models.KatalogUpdate;
import com.novelCheck.updateList.models.UserKatalog;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/update")
public class UpdateList {

    @RequestMapping("/users/{userID}")
    public UserKatalog getUserKatalog(@PathVariable("userID") String userID){//kiedyś będzie to mieć wpływ
    List<KatalogUpdate> katalog = Arrays.asList(
            new KatalogUpdate("the-second-coming-of-gluttony","NovelUpdates"),
            new KatalogUpdate("second-life-ranker","NovelUpdates"),
            new KatalogUpdate("overgeared","NovelUpdates")
    );
        UserKatalog userKatalog = new UserKatalog();
        userKatalog.setNovels(katalog);
        return userKatalog;//
    }


}
