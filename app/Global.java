import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.api.mvc.RequestHeader;
import play.mvc.Result;

import static play.mvc.Results.internalServerError;


public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        Logger.info("Backend-application is up and rollin'! Have fun!");
    }

    @Override
    public void onStop(Application app) {
        Logger.info("Backend-application have been shut down.");
    }

    /*
    public F.Promise<Result> onError(RequestHeader request, Throwable t) {
        return F.Promise.<Result>pure(internalServerError(views.html.errorPage.render(t)
        ));
    }
    */

    public Result onError(RequestHeader request, Throwable t) {
        return internalServerError(views.html.errorPage.render(t));

    }


}
