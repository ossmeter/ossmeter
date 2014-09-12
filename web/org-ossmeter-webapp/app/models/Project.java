package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Project extends Model {

    @Id
    public Long id;
    public String name;
    public String shortName;
    public String url;
    public String desc;
    @ManyToMany(cascade = CascadeType.REMOVE)
    public List<User> users = new ArrayList<User>();

    public Project() {
    
    }
    
    public Project(String name, String shortName, String url, String description) {
        this.name = name;
        this.shortName = shortName;
        this.url = url;
        this.desc = description;
    }
    
    public Project(String name, String description, User owner) {
        this.name = name;
        this.desc = description;
        this.users.add(owner);
    }

    public static Model.Finder<Long,Project> find = new Model.Finder<Long, Project>(Long.class, Project.class);
    
    public static Project create(String name, String folder) {
        Project project = new Project();
        project.name = name;
        project.save();
        return project;
    }

//    public static Project create(String name, String folder, String owner) {
//        Project project = new Project(name, folder, User.find.ref(owner));
//        project.save();
//        project.saveManyToManyAssociations("users");
//        return project;
//    }

    public static List<Project> findInvolving(String user) {
        return find.where()
            .eq("users.email", user)
            .findList();
    }
}