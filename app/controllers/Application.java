package controllers;

import com.avaje.ebean.Ebean;
import forms.ChangeStudentInfoForm;
import models.Activity;
import models.Course;
import models.Examination;
import models.Student;
import play.*;
import play.data.Form;
import play.mvc.*;

import play.libs.mailer.Email;
import play.libs.mailer.MailerPlugin;


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

    public static Result ejRealiserad(){
        return ok(ejRealiserad.render());
    }

    public static Result loginPage (String response) {
        return ok(login.render(response));
    }

    // Actions
    public static Result login() {
        Student student = Form.form(Student.class).bindFromRequest().get();
        String failureTitle = "Gick ej att logga in!";
        String failure = "Fel användarnamn eller lösenord.";

        try{
            if(!Student.findByUsernameAndPassword(student.username, student.password).equals(null)){
                session("loggedIn", student.username);
                return redirect(controllers.routes.Application.index());
            }
            flash("failureTitle", failureTitle);
            flash("failure", failure);
            return redirect(controllers.routes.Application.loginPage(failure));
        }
        catch(Exception e){
            Logger.info(e.getMessage());
            flash("failureTitle", failureTitle);
            flash("failure", failure);
            return redirect(controllers.routes.Application.loginPage(failure));
        }

    }

    public static Result logout() {
        session().remove("loggedIn");
        return redirect(controllers.routes.Application.loginPage("Du har nu loggats ut."));
    }

    // Change student information
    // I front end på alla inputs: Attributet "name"= fälten i domänobjektet (username, osv)
    public static Result changeStudentInformation() {
        ChangeStudentInfoForm changeStudentInfoForm = Form.form(ChangeStudentInfoForm.class).bindFromRequest().get();
        String successTitle = "Uppdaterat!";
        String success = "Du har nu uppdaterat dina användaruppgifter.";
        String failureTitle = "Fel!";
        String failure = "Det gick inte att uppdatera dina användaruppgifter.";

        String loggedInUser = session().get("loggedIn");
        try{
            if(!Student.findByUsername(loggedInUser).equals(null)){
                Student studentToChange = Student.findByUsername(loggedInUser);

                // String co, String streetAdress, String zipcode, String city, String email
                studentToChange.changeStudentInformation(
                        changeStudentInfoForm.co,
                        changeStudentInfoForm.streetAdress,
                        changeStudentInfoForm.zipcode,
                        changeStudentInfoForm.city,
                        changeStudentInfoForm.email,
                        changeStudentInfoForm.notifyByEmail
                );
                Activity activity = new Activity("Du har uppdaterat dina uppgifter");
                studentToChange.activities.add(activity);
                studentToChange.save();
                session().put("loggedIn", studentToChange.username);
                Logger.info("Studentinformation ändrad.");


                if(studentToChange.notifyByEmail){
                    // Mail
                    Email mail = new Email();
                    mail.setSubject("Ladok 2.0 - Uppgifter uppdaterade!");
                    mail.setFrom("Ladok 2.0 <devjungler@gmail.com>");
                    mail.addTo("TO <"+studentToChange.email+">");
                    // sends text, HTML or both...
                    mail.setBodyText("A text message");
                    mail.setBodyHtml(
                            "<html><body><h3>"+activity.text+"</h3></body></html>");
                    MailerPlugin.send(mail);
                }

                flash("successTitle", successTitle);
                flash("success", success);


            }
        }
        catch(Exception e) {
            Logger.info(e.getMessage());
            flash("failureTitle", failureTitle);
            flash("failure", failure);

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

        String loggedInUser = session().get("loggedIn");
        try {
            if (!Student.findByUsername(loggedInUser).equals(null)) {
                Student studentToChange = Student.findByUsername(loggedInUser);

                Activity activity = new Activity("Du har registrerat dig till kurs " + courseToChange.name);
                studentToChange.activities.add(activity);
                studentToChange.save();


                if (studentToChange.notifyByEmail) {
                    // Mail
                    Email mail = new Email();
                    mail.setSubject("Ladok 2.0 - Registrerad till kurs!");
                    mail.setFrom("Ladok 2.0 <devjungler@gmail.com>");
                    mail.addTo("TO <" + studentToChange.email + ">");
                    // sends text, HTML or both...
                    mail.setBodyText("A text message");
                    mail.setBodyHtml(
                            "<html><body><h3>" + activity.text + "</h3></body></html>");
                    MailerPlugin.send(mail);
                }
                String successTitle = "Registrerad!";
                String success = "Du har nu registrerat dig till kurs " + courseToChange.name + "!";

                flash("successTitle", successTitle);
                flash("success", success);


            }
        } catch (Exception e) {
            Logger.info(e.getMessage());
            String failureTitle = "Kunde ej registrera!";
            String failure = "Det gick inte att registrera dig till kurs " + courseToChange.name + ".";

            flash("failureTitle", failureTitle);
            flash("failure", failure);

        }


        return redirect(controllers.routes.Application.index());
    }

    public static Result deregisterForCourse() {
        Course course = Form.form(Course.class).bindFromRequest().get();
        Course courseToChange = Ebean.find(Course.class, course.id);
        courseToChange.deregister();
        courseToChange.save();

        String loggedInUser = session().get("loggedIn");
        try {
            if (!Student.findByUsername(loggedInUser).equals(null)) {
                Student studentToChange = Student.findByUsername(loggedInUser);

                Activity activity = new Activity("Du har avregistrerat från kurs " + courseToChange.name);
                studentToChange.activities.add(activity);
                studentToChange.save();


                if (studentToChange.notifyByEmail) {
                    // Mail
                    Email mail = new Email();
                    mail.setSubject("Ladok 2.0 - Avregistrerad från kurs!");
                    mail.setFrom("Ladok 2.0 <devjungler@gmail.com>");
                    mail.addTo("TO <" + studentToChange.email + ">");
                    // sends text, HTML or both...
                    mail.setBodyText("A text message");
                    mail.setBodyHtml(
                            "<html><body><h3>" + activity.text + "</h3></body></html>");
                    MailerPlugin.send(mail);
                }
                String successTitle = "Avregistrerad!";
                String success = "Du har nu avregistrerat dig från kursen " + courseToChange.name + "!";
                flash("successTitle", successTitle);
                flash("success", success);

            }
        } catch (Exception e) {
            Logger.info(e.getMessage());
            String failureTitle = "Kunde ej avregistrera!";
            String failure = "Det gick inte att avregistrera dig från kurs " + courseToChange.name + ".";
            flash("failureTitle", failureTitle);
            flash("failure", failure);

        }


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

        String loggedInUser = session().get("loggedIn");
        try {
            if (!Student.findByUsername(loggedInUser).equals(null)) {
                Student studentToChange = Student.findByUsername(loggedInUser);

                Activity activity = new Activity("Du har anmält dig till " + examinationToChange.name);
                studentToChange.activities.add(activity);
                studentToChange.save();


                if (studentToChange.notifyByEmail) {
                    // Mail
                    Email mail = new Email();
                    mail.setSubject("Ladok 2.0 - Anmäld till tentamen!");
                    mail.setFrom("Ladok 2.0 <devjungler@gmail.com>");
                    mail.addTo("TO <" + studentToChange.email + ">");
                    // sends text, HTML or both...
                    mail.setBodyText("A text message");
                    mail.setBodyHtml(
                            "<html><body><h3>" + activity.text + "</h3></body></html>");
                    MailerPlugin.send(mail);
                }
                String successTitle = "Anmäld!";
                String success = "Du har nu anmält dig till " + examinationToChange.name + "!";
                flash("successTitle", successTitle);
                flash("success", success);

            }
        } catch (Exception e) {
            Logger.info(e.getMessage());
            String failureTitle = "Kunde ej anmäla!";
            String failure = "Det gick inte att anmäla dig till tentamen " + examinationToChange.name + ".";
            flash("failureTitle", failureTitle);
            flash("failure", failure);
        }


        return redirect(controllers.routes.Application.index());
    }

    public static Result deregisterForExamination() {
        Examination examination = Form.form(Examination.class).bindFromRequest().get();
        Examination examinationToChange = Ebean.find(Examination.class, examination.id);
        examinationToChange.deregister();
        examinationToChange.save();

        String loggedInUser = session().get("loggedIn");
        try {
            if (!Student.findByUsername(loggedInUser).equals(null)) {
                Student studentToChange = Student.findByUsername(loggedInUser);

                Activity activity = new Activity("Du har avanmält dig från " + examinationToChange.name);
                studentToChange.activities.add(activity);
                studentToChange.save();


                if (studentToChange.notifyByEmail) {
                    // Mail
                    Email mail = new Email();
                    mail.setSubject("Ladok 2.0 - Avanmäld från tentamen!");
                    mail.setFrom("Ladok 2.0 <devjungler@gmail.com>");
                    mail.addTo("TO <" + studentToChange.email + ">");
                    // sends text, HTML or both...
                    mail.setBodyText("A text message");
                    mail.setBodyHtml(
                            "<html><body><h3>" + activity.text + "</h3></body></html>");
                    MailerPlugin.send(mail);
                }
                String successTitle = "Avanmäld!";
                String success = "Du har nu avavanmält dig från " + examinationToChange.name + "!";
                flash("successTitle", successTitle);
                flash("success", success);

            }
        } catch (Exception e) {
            Logger.info(e.getMessage());
            String failureTitle = "Kunde ej avanmäla!";
            String failure = "Det gick inte att avanmäla dig från " + examinationToChange.name + ".";
            flash("failureTitle", failureTitle);
            flash("failure", failure);
        }


        return redirect(controllers.routes.Application.index());
    }



}
