package com.novelCheck.updatedbH2.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.novelCheck.updatedbH2.model.KatalogUpdate;

import java.util.List;

public interface DbRepo extends JpaRepository<KatalogUpdate, Long> {

    List<KatalogUpdate> findByNovelID (String novelID);
    List<KatalogUpdate> findBynovelID(String novelID);
    KatalogUpdate findByID (long ID);
}
