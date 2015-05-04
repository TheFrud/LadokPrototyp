package controllers;

import models.Student;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

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

    // ----------------------------------


    // Student methods
    public static Result addStudent() {
        Student student = Form.form(Student.class).bindFromRequest().get();
        student.save();
        return redirect(controllers.routes.Administration.index());
    }



}
