package models;


import play.Logger;
import play.db.ebean.Model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Fredrik on 2015-05-04.
 */

@Entity
public class Course extends Model{

    @Id
    public long id;

    public Course(String name, String code, String points, String rate, String status, String grade){
        this.name = name;
        this.code = code;
        this.points = points;
        this.rate = rate;
        this.status = status;
        this.grade = grade;
    }

    public String toString() {
        return name;
    }

    public void setRegisterDate(String firstDateToRegister, String lastDateToRegister){
        try{
            this.firstDateToRegister = firstDateToRegister;
            this.lastDateToRegister = lastDateToRegister;
        }
        catch (Exception e){
            Logger.info(e.getMessage());
        }
    }

    public void setStartAndEndDate(String startDate, String endDate){
        try{
            this.startDate = startDate;
            this.endDate = endDate;
        }
        catch (Exception e){
            Logger.info(e.getMessage());
        }
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String name;
    public String code;
    public String points;
    public String grade;
    public String rate;
    // "CanRegister" "Registered" "Completed"
    public String status;

    // Till for-loopar i front end.
    public List<Examination> examinationsCanRegister() {
        List<Examination> innerExaminationsCanRegister = new ArrayList<>();
        for(Examination examination: examinations) {
            if(examination.status.equals("CanRegister")){
                innerExaminationsCanRegister.add(examination);
            }
        }
        return innerExaminationsCanRegister;
    }

    public List<Examination> examinationsRegistered() {
        List<Examination> innerExaminationsRegistered = new ArrayList<>();
        for(Examination examination: examinations) {
            if(examination.status.equals("Registered")){
                innerExaminationsRegistered.add(examination);
            }
        }
        return innerExaminationsRegistered;
    }

    public List<Examination> examinationsCompleted() {
        List<Examination> innerExaminationsCompleted = new ArrayList<>();
        for(Examination examination: examinations) {
            if(examination.status.equals("Completed")){
                innerExaminationsCompleted.add(examination);
            }
        }
        return innerExaminationsCompleted;
    }

    @OneToMany(cascade= CascadeType.ALL)
    public List<Examination> examinations  = new ArrayList<Examination>();

    /*
    @OneToMany(cascade= CascadeType.ALL)
    public List<Examination> examinationsCompleted  = new ArrayList<Examination>();

    @OneToMany(cascade= CascadeType.ALL)
    public List<Examination> examinationsRegistered  = new ArrayList<Examination>();

    @OneToMany(cascade= CascadeType.ALL)
    public List<Examination> examinationsCanRegister  = new ArrayList<Examination>();
    */

    @OneToMany(cascade= CascadeType.ALL)
    public List<Assignment> assignments = new ArrayList<Assignment>();

    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public String firstDateToRegister;
    public String lastDateToRegister;
    public String startDate;
    public String endDate;


    // Logic
    public void register() {
        this.status = "Registered";
    }

    public void deregister() {
        this.status = "CanRegister";
    }
}



/*--
    public void sendEmail() throws MessagingException {

        String inMail = mail;
        int inSendControl = 1; //Satt en 1:a om kontrollmejl ska skickas.
        String inSubject = "Du ar nu registrerad pa kursen xxxx";
        String inControlSubject = "Ny anmalan till + course.name ;
        String inContent = " Hej bla bla bla"
        String inControlContent = "Test bla bla";

        EmailSender.start(inMail, inSendControl, inSubject, inControlSubject, inContent, inControlContent);
    }

    --*/

