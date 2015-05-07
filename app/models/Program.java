package models;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;
import play.libs.F;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Fredrik on 2015-05-04.
 */

@Entity
public class Program extends Model {

    @Id
    public long id;

    public Program(String name, String code, String points, String rate) {
        this.name = name;
        this.code = code;
        this.points = points;
        this.rate = rate;
    }

    public void setStartAndEndDate(GregorianCalendar startDate, GregorianCalendar endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String name;
    public String code;
    public String points;
    public String rate;

    public GregorianCalendar startDate;
    public GregorianCalendar endDate;


    @OneToMany(cascade= CascadeType.ALL)
    public List<Course> courses = new ArrayList<Course>();

    // Till for-loopar i front end.
    public List<Course> coursesCanRegister() {
        List<Course> innerCanRegister = new ArrayList<>();
        for(Course course: courses) {
            if(course.status.equals("CanRegister")){
                innerCanRegister.add(course);
            }
        }
        return innerCanRegister;
    }

    public List<Course> coursesRegistered() {
        List<Course> innerCoursesRegistered = new ArrayList<>();
        for(Course course: courses) {
            if(course.status.equals("Registered")){
                innerCoursesRegistered.add(course);
            }
        }
        return innerCoursesRegistered;
    }

    public List<Course> coursesCompleted() {
        List<Course> innerCoursesCompleted = new ArrayList<>();
        for(Course course: courses) {
            if(course.status.equals("Completed")){
                innerCoursesCompleted.add(course);
            }
        }
        return innerCoursesCompleted;
    }

    /*
    @OneToMany(cascade= CascadeType.ALL)
    public List<Course> coursesCompleted = new ArrayList<Course>();

    @OneToMany(cascade= CascadeType.ALL)
    public List<Course> coursesRegistered = new ArrayList<Course>();

    @OneToMany(cascade= CascadeType.ALL)
    public List<Course> coursesCanRegister = new ArrayList<Course>();
    */

}
