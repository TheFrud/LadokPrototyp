package models;


import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Fredrik on 2015-05-04.
 */

@Entity
public class Examination extends Model {

    @Id
    public long id;

    public Examination(String name, String points, String grade) {
        this.name = name;
        this.points = points;
        this.grade = grade;
    }

    public void setRegisterDate(GregorianCalendar firstDateToRegister, GregorianCalendar lastDateToRegister) {
        this.firstDateToRegister = firstDateToRegister;
        this.lastDateToRegister = lastDateToRegister;
    }

    public void setTimeStartAndEnd(String timeStart, String timeEnd) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public void setExaminationDate(GregorianCalendar examinationDate) {
        this.examinationDate = examinationDate;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String name;
    public String points;
    public String grade;

    public GregorianCalendar examinationDate;
    public GregorianCalendar firstDateToRegister;
    public GregorianCalendar lastDateToRegister;
    public String timeStart;
    public String timeEnd;
    public String local;
}
