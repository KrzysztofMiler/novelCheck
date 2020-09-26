package com.novelCheck.updatedbH2;

import com.novelCheck.updatedbH2.model.KatalogUpdate;
import com.novelCheck.updatedbH2.model.UserUser;
import com.novelCheck.updatedbH2.repository.KatalogUpdateRepo;
import com.novelCheck.updatedbH2.repository.UserUserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.Max;
import java.util.Arrays;
import java.util.List;

@EnableEurekaClient
@SpringBootApplication
public class UpdateDbH2Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext =
			SpringApplication.run(UpdateDbH2Application.class, args);

		KatalogUpdateRepo katalogUpdateRepo = configurableApplicationContext.getBean(KatalogUpdateRepo.class);

		UserUserRepo userUserRepo = configurableApplicationContext.getBean(UserUserRepo.class);


		UserUser user1 = new UserUser("asd","asd@gmail.com");
		UserUser user2 = new UserUser("asd2","asd2222@gmail.com");
		UserUser user3 = new UserUser("asd3","asd333333@gmail.com");
		List<UserUser> userList = Arrays.asList(user1,user2,user3);


		KatalogUpdate SCOG = new KatalogUpdate("the-second-coming-of-gluttony","NovelUpdates");
		KatalogUpdate SLF = new KatalogUpdate("second-life-ranker","NovelUpdates");
		KatalogUpdate OVG = new KatalogUpdate("overgeared","NovelUpdates");
		KatalogUpdate HWFWM = new KatalogUpdate("41306/he-who-fights-with-monsters", "");
		List<KatalogUpdate> katalogUpdateList = Arrays.asList(SCOG,SLF,OVG,HWFWM);

		katalogUpdateRepo.saveAll(katalogUpdateList);

		user1.subToNovel(SCOG);
		user1.subToNovel(SLF);
		user2.subToNovel(SCOG);
		user3.subToNovel(OVG);
		user3.subToNovel(HWFWM);

		userUserRepo.saveAll(userList);
	}
	/*@Bean//jak narazie to wpisuje do db
    CommandLineRunner run(CrudRepository repository){
	    return args -> {
            repository.save(new KatalogUpdate("the-second-coming-of-gluttony","NovelUpdates"));
            repository.save(new KatalogUpdate("second-life-ranker","NovelUpdates"));
			repository.save(new KatalogUpdate("overgeared","NovelUpdates"));
			repository.save(new KatalogUpdate("41306/he-who-fights-with-monsters", ""));
        };//trzeba będzie chyba zmienić co do ost rekordu
    }*/
}
