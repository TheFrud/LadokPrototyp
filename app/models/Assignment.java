package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Fredrik on 2015-05-07.
 */

@Entity
public class Assignment extends Model{

    @Id
    public long id;



    public Assignment(String name, String points, String grade) {
        this.name = name;
        this.points = points;
        this.grade = grade;
    }

    public String name;
    public String points;
    public String grade;

    // SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    // public String date = "";
    // Behövs även fixa i contructorn om denna ska användas.

}
