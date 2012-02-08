package controllers;

import models.YabeUser;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

import java.util.*;

@With(Secure.class)
public class Admin extends Controller {
	
	@Before
	static void setConnectedUser() {
		if(Security.isConnected()) {
			YabeUser user = YabeUser.find("byEmail", Security.connected()).first();
			renderArgs.put("user", user.fullname);
		}
	}
	
	public static void index() {
		render();
	}
}
