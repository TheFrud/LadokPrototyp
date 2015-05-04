package models;


import play.db.ebean.Model;

import java.util.Date;

/**
 * Created by Fredrik on 2015-05-04.
 */

// @Entity
public class Examination extends Model {

    public long id;
    public String name;
    public String points;
    public String grade;

    public Date examinationDate;
    public Date firstDateToRegister;
    public Date lastDateToRegister;
    public Date timeStart;
    public Date timeEnd;
    public String local;
}
