package com.novelCheck.updatedb.repository;

import com.novelCheck.updatedb.models.KatalogUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovelRepo extends JpaRepository {

    List<KatalogUpdate> findByUserName(String username);
}
