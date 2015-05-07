package controllers;

import com.avaje.ebean.Ebean;
import models.Course;
import models.Examination;
import models.Student;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    // Navigation
    public static Result index() {
        String isStudentLoggedIn = session("loggedIn");
        if(isStudentLoggedIn != null) {
            Student student = Student.findByUsername(isStudentLoggedIn);
            return ok(index.render("", student));
        } else {
            return redirect(controllers.routes.Application.loginPage("Logga in för att kunna se dina studier."));
        }
    }

    public static Result readme() {
        return ok(readme.render());
    }

    public static Result loginPage (String response) {
        return ok(login.render(response));
    }

    // Actions
    public static Result login() {
        Student student = Form.form(Student.class).bindFromRequest().get();
        try{
            if(!Student.findByUsernameAndPassword(student.username, student.password).equals(null)){
                session("loggedIn", student.username);
                return redirect(controllers.routes.Application.index());
            }
            return redirect(controllers.routes.Application.loginPage("Fel användarnamn eller lösenord."));
        }
        catch(Exception e){
            Logger.info(e.getMessage());
            return redirect(controllers.routes.Application.loginPage("Fel användarnamn eller lösenord."));
        }

    }

    public static Result logout() {
        session().remove("loggedIn");
        return redirect(controllers.routes.Application.loginPage("Du har nu loggats ut."));
    }

    // Change student information
    // I front end på alla inputs: Attributet "name"= fälten i domänobjektet (username, osv)
    public static Result changeStudentInformation() {
        Student student = Form.form(Student.class).bindFromRequest().get();
        String loggedInUser = session().get("loggedIn");
        try{
            if(!Student.findByUsername(loggedInUser).equals(null)){
                Student studentToChange = Student.findByUsername(loggedInUser);
                // String username, String password, String ssn, String firstName, String surname, String co, String streetAdress, String zipcode, String city, String email
                studentToChange.changeStudentInformation(
                        student.username,
                        student.password,
                        student.ssn,
                        student.firstName,
                        student.surname,
                        student.co,
                        student.streetAdress,
                        student.zipcode,
                        student.city,
                        student.email
                );
                studentToChange.save();
                session().put("loggedIn", studentToChange.username);
                Logger.info("Studentinformation ändrad.");
            }
        }
        catch(Exception e) {
            Logger.info(e.getMessage());

        }
        return redirect(controllers.routes.Application.index());


    }

    // Register for course
    // Det som behövs från front end är kursens id.
    // Bara att sätta värdet på attributet "name" på inputen till "id". Samma som modellen.
    public static Result registerForCourse() {
        Course course = Form.form(Course.class).bindFromRequest().get();
        Course courseToChange = Ebean.find(Course.class, course.id);
        courseToChange.register();
        courseToChange.save();
        Logger.info("Registered on course.");
        return redirect(controllers.routes.Application.index());
    }
    // Register for examination
    // Det som behövs från front end är tentans id.
    // Bara att sätta värdet på attributet "name" på inputen till "id". Samma som modellen.
    public static Result registerForExamination() {
        Examination examination = Form.form(Examination.class).bindFromRequest().get();
        Examination examinationToChange = Ebean.find(Examination.class, examination.id);
        examinationToChange.register();
        examinationToChange.save();
        Logger.info("Registered on examination.");
        return redirect(controllers.routes.Application.index());
    }




}
