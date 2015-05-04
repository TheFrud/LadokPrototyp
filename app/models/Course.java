package models;

import play.db.ebean.Model;

import java.util.Date;

/**
 * Created by Fredrik on 2015-05-04.
 */

// @Entity
public class Course extends Model{

    public long id;
    public String code;
    public String name;
    public String points;
    public String grade;
    public String rate;
    public String city;

    // Lista
    /*
    public examinationsCompleted List<Examination> = new ArrayList<Examination>();
    public examinationsRegistered List<Examination> = new ArrayList<Examination>();
    public examinationsCanRegister List<Examination> = new ArrayList<Examination>();
     */

    public Date firstDateToRegister;
    public Date lastDateToRegister;
    public Date startDate;
}
