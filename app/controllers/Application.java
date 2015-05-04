package controllers;

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
}
