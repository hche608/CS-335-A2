package communityblogger.services;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import communityblogger.dto.User;

public class BlogResourceTestEntry {

	private Logger _logger = LoggerFactory.getLogger(BlogResourceTestEntry.class);

	private static final String WEB_SERVICE_URI = "http://localhost:10000/services/blogger";

	private static Client _client;

	/**
	 * One-time setup method that creates a Web service client.
	 */
	@BeforeClass
	public static void setUpClient() {
		_client = ClientBuilder.newClient();
	}

	/**
	 * Runs before each unit test restore Web service database. This ensures
	 * that each test is independent; each test runs on a Web service that has
	 * been initialised with a common set of Parolees.
	 */
	@Before
	public void reloadServerData() {
		Response response = _client.target(WEB_SERVICE_URI).request().put(null);
		response.close();

		// Pause briefly before running any tests. Test addParoleeMovement(),
		// for example, involves creating a timestamped value (a movement) and
		// having the Web service compare it with data just generated with
		// timestamps. Joda's Datetime class has only millisecond precision,
		// so pause so that test-generated timestamps are actually later than
		// timestamped values held by the Web service.
		
		 User _dto_user = new User("test1","hello","world");
			
		 _logger.info("Creating a new User ...");
		 response = _client
		 .target(WEB_SERVICE_URI + "/user").request()
		 .post(Entity.xml(_dto_user));
		 if (response.getStatus() != 201) {
		 _logger.error("Failed to create User; Web service responded with: " +
		 response.getStatus());
		 fail("Failed to create new User");
		 }
		
		 String location = response.getLocation().toString();
		 _logger.info("URI for new User: " + location);
		
		 response.close();
		 
		 _dto_user = new User("test2","Chen","Hao");
			
		 _logger.info("Creating a new User ...");
		 response = _client
		 .target(WEB_SERVICE_URI + "/user").request()
		 .post(Entity.xml(_dto_user));
		 if (response.getStatus() != 201) {
		 _logger.error("Failed to create User; Web service responded with: " +
		 response.getStatus());
		 fail("Failed to create new User");
		 }
		
		 location = response.getLocation().toString();
		 _logger.info("URI for new User: " + location);
		
		 response.close();
		
		
		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * One-time finalisation method that destroys the Web service client.
	 */
	@AfterClass
	public static void destroyClient() {
		_client.close();
	}

	/**
	 * Tests that the Web service can create a new User.
	 */
	 @Test
	 public void addEntry() {

	

	
	 }
}
