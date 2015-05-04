package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fredrik on 2015-05-04.
 */

@Entity
public class Student extends Model {

    @Id
    public long id;

    @Constraints.Required
    public String username;

    @Constraints.Required
    public String password;

    public String ssn;
    public String firstName;
    public String surname;
    public String streetAdress;
    public String zipcode;
    public String city;
    public String email;

    /*
    public List<Program> programs  = new ArrayList<Program>();
    public List<Activity> activities;
    */

    public static Finder<Long, Student> find = new Finder<Long, Student>(Long.class, Student.class);

    public static Student findByUsernameAndPassword(String username, String password) {
        return find.where().eq("username", username.toLowerCase()).eq("password", password).findUnique();
    }

    public static Student findByUsername(String username) {
        return find.where().eq("username", username.toLowerCase()).findUnique();
    }
}
