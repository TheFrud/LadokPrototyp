package models;

import play.db.ebean.Model;

import java.util.Date;

/**
 * Created by Fredrik on 2015-05-04.
 */


// @Entity
public class Activity extends Model {

    public long id;
    public String text;
    public Date date;

    public Activity(String text) {
        this.date = new Date();
        this.text = text;
    }
}
