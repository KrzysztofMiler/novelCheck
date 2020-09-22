package com.novelCheck.updatedbH2.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.novelCheck.updatedbH2.model.KatalogUpdate;

import java.util.List;

public interface DbRepo extends CrudRepository<KatalogUpdate, Long> {



    //KatalogUpdate findByNovelID (String novelID);//tak uzyskam pojedyńczy wynik
    List<KatalogUpdate> findByNovelID (String novelID);

    List<KatalogUpdate> findByStrona (String strona);//tak pare

    //KatalogUpdate findByID (long ID);//zbyteczne
}
