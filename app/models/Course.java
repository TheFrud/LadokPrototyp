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
public class Course extends Model{

    @Id
    public long id;

    public Course(String name, String code, String points, String rate){
        this.name = name;
        this.code = code;
        this.points = points;
        this.rate = rate;
    }

    public String toString() {
        return name;
    }

    public void setRegisterDate(GregorianCalendar firstDateToRegister, GregorianCalendar lastDateToRegister) {
        this.firstDateToRegister = firstDateToRegister;
        this.lastDateToRegister = lastDateToRegister;
    }

    public void setStartAndEndDate(GregorianCalendar startDate, GregorianCalendar endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String name;
    public String code;
    public String points;
    public String grade;
    public String rate;
    public String city;

    @OneToMany(cascade= CascadeType.ALL)
    public List<Examination> examinationsCompleted  = new ArrayList<Examination>();

    @OneToMany(cascade= CascadeType.ALL)
    public List<Examination> examinationsRegistered  = new ArrayList<Examination>();

    @OneToMany(cascade= CascadeType.ALL)
    public List<Examination> examinationsCanRegister  = new ArrayList<Examination>();

    public GregorianCalendar firstDateToRegister;
    public GregorianCalendar lastDateToRegister;
    public GregorianCalendar startDate;
    public GregorianCalendar endDate;
}
