package com.novelCheck.updatedbH2.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USERUSER")
public class UserUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SUBNOVEL")//jak narazie zrobimy bidirectional ale potem chyab unidir będzie lepiej
    @ManyToMany//chyba jest uni
    @Fetch(FetchMode.JOIN)//powinno nie powodoćać bł z lazy join
    @Cascade({org.hibernate.annotations.CascadeType.DETACH, org.hibernate.annotations.CascadeType.MERGE})
    @JoinTable(name = "UserNovel",
            joinColumns = @JoinColumn(name = "user_id"),//owner
            inverseJoinColumns = @JoinColumn(name = "novel_id"))
    private Set<KatalogUpdate> subNovel = new HashSet<>();

    public UserUser(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public void subToNovel(KatalogUpdate katalogUpdate){
        subNovel.add(katalogUpdate);
    }

    public Set<KatalogUpdate> getSubNovel() {
        return subNovel;
    }

    public UserUser() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
