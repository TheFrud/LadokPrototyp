package models;

import com.avaje.ebean.Ebean;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Fredrik on 2015-05-04.
 */

@Entity
public class Student extends Model {

    @Id
    public long id;

    public String username;

    public String password;

    public String ssn;
    public String firstName;
    public String surname;
    public String co;
    public String streetAdress;
    public String zipcode;
    public String city;
    public String email;

    public boolean notifyByEmail = true;

    public String toString(){
        return username + " (" + firstName + " " + surname + ")";
    }

    public void changeStudentInformation(String co, String streetAdress, String zipcode, String city, String email, boolean notifyByEmail){
        this.co = co;
        this.streetAdress = streetAdress;
        this.zipcode = zipcode;
        this.city = city;
        this.email = email;
        this.notifyByEmail = notifyByEmail;
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

    // Hämta alla aktiviteter
    public List<Activity> getAllActivities() {

        return activities;
    }
    /*
    public List<Activity> getAllActivities() {

        List<Activity> reversedList = activities;
        Collections.reverse(reversedList);
        return reversedList;
    }
    */

    // Hämta 3 aktiviteter
    public List<Activity> getThreeActivities() {
        List<Activity> innerThreeActivities = new ArrayList<>();
        for(Activity activity: Lists.reverse(activities)) {
            if(innerThreeActivities.size() == 3){
                return innerThreeActivities;
            }
            innerThreeActivities.add(activity);
        }
        return innerThreeActivities;
    }

    /*
    public List<Activity> getThreeActivities() {
        List<Activity> innerThreeActivities = new ArrayList<>();
        for(Activity activity: Lists.reverse(activities)) {
            if(innerThreeActivities.size() == 3){
                return innerThreeActivities;
            }
            innerThreeActivities.add(activity);
        }
        return innerThreeActivities;
    }
    */

    public static Finder<Long, Student> find = new Finder<Long, Student>(Long.class, Student.class);

    public static Student findByUsernameAndPassword(String username, String password) {
        return find.where().eq("username", username.toLowerCase()).eq("password", password).findUnique();
    }

    public static Student findByUsername(String username) {
        return find.where().eq("username", username.toLowerCase()).findUnique();
    }

}
