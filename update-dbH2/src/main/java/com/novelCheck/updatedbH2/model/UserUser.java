package com.novelCheck.updatedbH2.model;

import javax.persistence.*;
import java.util.ArrayList;
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
    @JoinTable(name = "UserNovel",
            joinColumns = @JoinColumn(name = "user_id"),//owner
            inverseJoinColumns = @JoinColumn(name = "novel_id"))
    private List<KatalogUpdate> subNovel = new ArrayList<>();

    public UserUser(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public void subToNovel(KatalogUpdate katalogUpdate){
        subNovel.add(katalogUpdate);
    }

    public List<KatalogUpdate> getSubNovel() {
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
