package controllers;

import play.*;
import play.cache.Cache;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.libs.Codec;
import play.libs.Images;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

	@Before
	static void addDefaults() {
		renderArgs.put("blogTitle", Play.configuration.getProperty("blog.title"));
		renderArgs.put("blogBaseline", Play.configuration.getProperty("blog.baseline"));
	}
	
    public static void index() {
    	Post frontPost = Post.find("order by postedAt desc").first();
    	List<Post> olderPosts = Post.find(
    			"order by postedAt desc"
    	).from(1).fetch(10);
        render(frontPost, olderPosts);
    }

    public static void show(Long id) {
    	Post post = Post.findById(id);
    	String randamID = Codec.UUID();
    	render(post, randamID);
    }
    
    public static void postComment(
    		Long postId, 
    		@Required(message="Auther is required") String author, 
    		@Required(message="A message is required") String content,
    		@Required(message="Please type the code") String code,
    		String randamID) {
    	Post post = Post.findById(postId);
    	validation.equals(
    			code, Cache.get(randamID)
    			).message("Invalid code. Please type it again");
    	if (validation.hasErrors()) {
    		render("Application/show.html", post, randamID);
    	}
    	post.addComment(author, content);
    	flash.success("Thanks for posting %s", author);
    	Cache.delete(randamID);
    	show(postId);
    }
    
    public static void captcha(String id) {
    	Images.Captcha captcha = Images.captcha();
    	String code = captcha.getText("#E4EAFD");
    	Cache.set(id, code, "10min");
    	renderBinary(captcha);
    }
}