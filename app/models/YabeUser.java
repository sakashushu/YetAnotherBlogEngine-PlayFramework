package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class YabeUser extends Model {
	
    public String email;
    public String password;
    public String fullname;
    public boolean isAdmin;

    public YabeUser(String email, String password, String fullname) {
    	this.email = email;
    	this.password = password;
    	this.fullname = fullname;
    }

    public static YabeUser connect(String email, String password) {
    	return find("byEmailAndPassword", email, password).first();
    }
}
