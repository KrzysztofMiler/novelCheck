package com.novelCheck.updatedbH2;

import com.novelCheck.updatedbH2.model.KatalogUpdate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

//@EnableEurekaClient
@SpringBootApplication
public class UpdateDbH2Application {

	public static void main(String[] args) {
		SpringApplication.run(UpdateDbH2Application.class, args);
	}


	@Bean//jak narazie to wpisuje do db
    CommandLineRunner run(CrudRepository repository){
	    return args -> {
            repository.save(new KatalogUpdate("the-second-coming-of-gluttony","NovelUpdates"));
            repository.save(new KatalogUpdate("second-life-ranker","NovelUpdates"));
			repository.save(new KatalogUpdate("overgeared","NovelUpdates"));
			repository.save(new KatalogUpdate("41306/he-who-fights-with-monsters", ""));
        };//trzeba będzie chyba zmienić co do ost rekordu
    }
}
