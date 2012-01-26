import models.YabeUser;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * 
 */

/**
 * @author sakashushu
 *
 */
@OnApplicationStart
public class Bootstrap extends Job {
	public void doJob() {
		// Check if the database is empty
		if (YabeUser.count() == 0) {
			Fixtures.loadModels("initial-data.yml");
		}
	}
}
