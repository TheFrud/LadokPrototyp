package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Fredrik on 2015-05-04.
 */


@Entity
public class Activity extends Model {

    @Id
    public long id;

    public String text = "";
    public String date = "";

    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public Activity(String text) {
        this.date = formater.format(new Date());
        this.text = text;
    }
}
