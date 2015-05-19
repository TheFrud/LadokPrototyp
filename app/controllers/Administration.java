package controllers;

import models.*;
import play.*;
import play.data.Form;
import play.libs.mailer.Email;
import play.libs.mailer.MailerPlugin;
import play.mvc.*;

import views.html.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Fredrik on 2015-05-04.
 */
public class Administration extends Controller{

    /*
    Metoder vi kan använda i utvecklingen av applikationen
    för att lägga till användare, osv.
     */

    // Navigation methods
    public static Result index(String response) {
        if(response == null) {
            return ok(admin_index.render(""));
        }

        return ok(admin_index.render(response));
    }

    public static Result student() {
        return ok(admin_student.render());
    }

    public static Result studier() {
        List<Student> students = Student.find.all();
        return ok(admin_studier.render(students));
    }

    // ----------------------------------


    // Student methods
    public static Result addStudent() {
        Student student = Form.form(Student.class).bindFromRequest().get();
        try{
            if(!Student.findByUsername(student.username).equals(null)){
                Logger.info("Studenten existerar redan. Välj ett annat användarnamn.");
                return redirect(controllers.routes.Administration.index("Studenten existerar redan. Välj ett annat användarnamn."));
            }
        }
        catch(Exception e) {}

        initiateStudentPrograms(student);

        Activity activity1 = new Activity("Studentkonto skapat!");
        Activity activity2 = new Activity("Registrerades på programmet Systemvetenskap.");

        student.activities.add(activity1);
        student.activities.add(activity2);
        student.save();

        if(student.notifyByEmail) {
            // Mail
            Email mail = new Email();
            mail.setSubject("Ladok 2.0 - Välkommen!");
            mail.setFrom("Ladok 2.0 <devjungler@gmail.com>");
            mail.addTo("TO <" + student.email + ">");
            // sends text, HTML or both...
            mail.setBodyText("A text message");
            mail.setBodyHtml(
                    "<html><body>" +
                            "<h3>" + activity1.text + "</h3><h4>" + activity2.text + "</h4>" +
                            "</body></html>");
            MailerPlugin.send(mail);
        }
        return redirect(controllers.routes.Administration.index("Studenten skapad. Gratulerar!"));
    }

    // WARNING for lots of text.
    // körs varje gång en student skapas.
    public static void initiateStudentPrograms(Student student) {

        // Initiera examinationer
        // name points grade status
        Examination examinationCompleted = new Examination("Informationsteknologi och informationssystem", "4.5", "VG", "Completed");
        examinationCompleted.setRegisterDate("2013-09-02","2013-09-23");
        examinationCompleted.setTimeStartAndEnd("09.00", "13.00");
        examinationCompleted.setExaminationDate("2013-10-01");
        examinationCompleted.setLocal("Hus Saga (3a)");
        examinationCompleted.setGradeStatus("inväntar");

        Examination examinationCompleted2 = new Examination("Informationsteknologi och informationssystem", "4.5", "VG", "Completed");
        examinationCompleted2.setRegisterDate("2013-09-02","2013-09-23");
        examinationCompleted2.setTimeStartAndEnd("09.00", "13.00");
        examinationCompleted2.setExaminationDate("2013-10-01");
        examinationCompleted2.setLocal("Hus Saga (3a)");
        examinationCompleted2.setGradeStatus("betygsatt");

        Examination examinationRegistered = new Examination("Verksamheter och information", "7.5", "", "Registered");
        examinationRegistered.setRegisterDate("2013-10-10", "2013-10-29");
        examinationRegistered.setTimeStartAndEnd("09.00", "13.00");
        examinationRegistered.setExaminationDate("2013-01-01");
        examinationRegistered.setLocal("Hus Saga (3c)");

        Examination examinationCanRegister = new Examination("Programmeringsteknik och databaser", "7.5", "", "CanRegister");
        examinationCanRegister.setRegisterDate("2014-03-01", "2013-03-18");
        examinationCanRegister.setTimeStartAndEnd("09.00", "13.00");
        examinationCanRegister.setExaminationDate("2014-04-05");
        examinationCanRegister.setLocal("Hus Saga (3b)");

        // Initiera assignments
        // String name, String points, String grade
        Assignment assignmentTig015_1 = new Assignment("Gruppuppgift", "7.5", "G");
        Assignment assignmentTig015_2 = new Assignment("Individuell skriftlig inlämningsuppgift", "3.0", "VG");
        Assignment assignmentTig016_1 = new Assignment("Gruppuppgift", "7.5", "G");
        Assignment assignmentTig058_1 = new Assignment("Gruppuppgift 1", "2.0", "");
        Assignment assignmentTig058_2 = new Assignment("Gruppuppgift 2", "2.5", "");
        Assignment assignmentTig058_3 = new Assignment("Gruppuppgift 3", "3.0", "");

        // Initiera kurser
        // String name, String code, String points, String rate, String grade
        Course courseCompleted = new Course("Informationsteknologi och informationssystem", "TIG015", "15", "100", "Completed", "G");
        courseCompleted.setRegisterDate("2013-08-01", "2013-08-20");
        courseCompleted.setStartAndEndDate("2013-08-29", "2013-10-25");
        courseCompleted.examinations.add(examinationCompleted);
        courseCompleted.assignments.add(assignmentTig015_1);
        courseCompleted.assignments.add(assignmentTig015_2);

        Course courseRegistered = new Course("Verksamheter och information", "TIG016", "15", "100", "Registered", "");
        courseRegistered.setRegisterDate("2013-09-01","2013-09-15");
        courseRegistered.setStartAndEndDate("2013-09-29","2014-01-11");
        courseRegistered.examinations.add(examinationRegistered);
        courseRegistered.assignments.add(assignmentTig016_1);

        Course courseCanRegister = new Course("Programmeringsteknik och databaser", "TIG058", "15", "100", "CanRegister", "");
        courseCanRegister.setRegisterDate("2014-01-01" , "2014-01-15");
        courseCanRegister.setStartAndEndDate("2014-01-25", "2014-03-29");
        courseCanRegister.examinations.add(examinationCanRegister);
        courseCanRegister.assignments.add(assignmentTig058_1);
        courseCanRegister.assignments.add(assignmentTig058_2);
        courseCanRegister.assignments.add(assignmentTig058_3);

        // Initiera program
        // String name, String code, String points, String rate
        Program program = new Program("Systemvetenskap", "SV105", "180", "100");
        program.setStartAndEndDate(new GregorianCalendar(2013, 8, 01), new GregorianCalendar(2016, 5, 8));
        program.courses.add(courseCompleted);
        program.courses.add(courseRegistered);
        program.courses.add(courseCanRegister);

        // Lägger till programmet till studenten
        student.programs.add(program);
    }


}
