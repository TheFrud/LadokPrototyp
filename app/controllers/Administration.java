package controllers;

import models.*;
import play.*;
import play.data.Form;
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
    public static Result index() {
        return ok(admin_index.render());
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
        initiateStudentPrograms(student);
        student.activities.add(new Activity("Studentkonto skapat!"));
        student.save();
        return redirect(controllers.routes.Administration.index());
    }

    public static void initiateStudentPrograms(Student student) {

        // Initiera examinationer
        // name points grade
        Examination examination = new Examination("Informationsteknologi och informationssystem", "7.5", "");
        examination.setRegisterDate(new GregorianCalendar(2013, 9, 02), new GregorianCalendar(2013, 9, 23));
        examination.setTimeStartAndEnd("09.00", "13.00");
        examination.setExaminationDate(new GregorianCalendar(2013, 10, 1));
        examination.setLocal("Hus Saga (3a)");

        // Initiera kurser
        // String name, String code, String points, String rate
        Course course = new Course("Informationsteknologi och informationssystem", "TIG015", "15", "100");
        course.setRegisterDate(new GregorianCalendar(2013, 8, 01), new GregorianCalendar(2013, 8, 20));
        course.setStartAndEndDate(new GregorianCalendar(2013, 8, 29), new GregorianCalendar(2013, 10, 25));
        course.examinationsCanRegister.add(examination);

        // Initiera program
        // String name, String code, String points, String rate
        Program program = new Program("Systemvetenskap", "SV105", "180", "100");
        program.setStartAndEndDate(new GregorianCalendar(2013, 8, 01), new GregorianCalendar(2016, 5, 8));
        program.coursesCanRegister.add(course);

        // Lägger till programmet till studenten
        student.programs.add(program);
    }


}
