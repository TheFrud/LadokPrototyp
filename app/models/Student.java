package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    public String co;
    public String streetAdress;
    public String zipcode;
    public String city;
    public String email;

    public String toString(){
        return username + " (" + firstName + " " + surname + ")";
    }

    public void changeStudentInformation(String username, String password, String ssn, String firstName, String surname, String co, String streetAdress, String zipcode, String city, String email){
        this.username = username;
        this.password = password;
        this.ssn = ssn;
        this.firstName = firstName;
        this.surname = surname;
        this.co = co;
        this.streetAdress = streetAdress;
        this.zipcode = zipcode;
        this.city = city;
        this.email = email;
    }

    @OneToMany(cascade= CascadeType.ALL)
    public List<Program> programs  = new ArrayList<Program>();

    @OneToMany(cascade= CascadeType.ALL)
    public List<Activity> activities = new ArrayList<Activity>();

    public static Finder<Long, Student> find = new Finder<Long, Student>(Long.class, Student.class);

    public static Student findByUsernameAndPassword(String username, String password) {
        return find.where().eq("username", username.toLowerCase()).eq("password", password).findUnique();
    }

    public static Student findByUsername(String username) {
        return find.where().eq("username", username.toLowerCase()).findUnique();
    }

}
