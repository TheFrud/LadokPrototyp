package models;

import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
    public List<Course> coursesCurrent = new ArrayList<Course>();

    @OneToMany(cascade= CascadeType.ALL)
    public List<Course> coursesCompleted = new ArrayList<Course>();

    @OneToMany(cascade= CascadeType.ALL)
    public List<Course> coursesRegistered = new ArrayList<Course>();

    @OneToMany(cascade= CascadeType.ALL)
    public List<Course> coursesCanRegister = new ArrayList<Course>();
}
