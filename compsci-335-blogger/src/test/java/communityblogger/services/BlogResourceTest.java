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

public class BlogResourceTest {

	private Logger _logger = LoggerFactory.getLogger(BlogResourceTest.class);

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
	 public void addUser() {
	 User _dto_user = new User("test","L Name","F Name");
	
	 _logger.info("Creating a new User ...");
	 Response response = _client
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
	
	 // Query the Web service for the new User.
	 User userFromService = _client.target(location).request()
	 .accept("application/xml").get(User.class);
	
	 // The original local User object (_user) should have a value equal
	 // to that of the User object representing User that is later
	 // queried from the Web service. The only exception is the value
	 // returned by getId(), because the Web service assigns this when it
	 // creates a User.
	 assertEquals(_dto_user.getUsername(), userFromService.getUsername());
	 assertEquals(_dto_user.getLastname(), userFromService.getLastname());
	 assertEquals(_dto_user.getFirstname(), userFromService.getFirstname());
	
	 }

	@Test
	public void testNewUserXML() {
		// Use ClientBuilder to create a new client that can be used to create
		// connections to the Web service.

		_logger.info("Creating a new User ...");

		// Create a XML representation for a new User.
		String xml = "<user username=\"ml\">" + "<first-name>Mark</first-name>" + "<last-name>Lundy</last-name>"
				+ "</user>";

		// Send a HTTP POST message, with a message body containing the XML,
		// to the Web service.
		Response response = _client.target(WEB_SERVICE_URI + "/user").request().post(Entity.xml(xml));

		// Expect a HTTP 201 "Created" response from the Web service.
		int status = response.getStatus();
		if (status != 201) {
			_logger.error("Failed to create User; Web service responded with: " + status);
			fail();
		}

		// Extract location header from the HTTP response message. This
		// should
		// give the URI for the newly created User.
		String location = response.getLocation().toString();
		_logger.info("URI for new User: " + location);

		// Close the connection to the Web service.
		response.close();

	}

	// @Test
	// public void testFetchUser() {
	// // Use ClientBuilder to create a new client that can be used to create
	// // connections to the Web service.
	// Client client = ClientBuilder.newClient();
	// try {
	// _logger.info("Creating a new Parolee ...");
	//
	// // Create a XML representation for a new Parolee.
	// String xml = "<user username=\"2\">"
	// + "<first-name>Mark2</first-name>"
	// + "<last-name>Lundy2</last-name>"
	// + "</user>";
	//
	// // Send a HTTP POST message, with a message body containing the XML,
	// // to the Web service.
	// Response response =
	// client.target("http://localhost:10000/services/blogger/user").request()
	// .post(Entity.xml(xml));
	//
	// // Expect a HTTP 201 "Created" response from the Web service.
	// int status = response.getStatus();
	// if (status != 201) {
	// _logger.error("Failed to create User; Web service responded with: " +
	// status);
	// fail();
	// }
	//
	// // Extract location header from the HTTP response message. This
	// // should
	// // give the URI for the newly created User.
	// String location = response.getLocation().toString();
	// _logger.info("URI for new User: " + location);
	//
	//
	// response =
	// client.target("http://localhost:10000/services/blogger/user/hche608").request().get();
	// status = response.getStatus();
	// if (status != 200) {
	// _logger.error("Failed to fetch User; Web service responded with: " +
	// status);
	// fail();
	// }
	//
	// response =
	// client.target("http://localhost:10000/services/blogger/user/ml").request().get();
	// status = response.getStatus();
	// if (status != 200) {
	// _logger.error("Failed to fetch User; Web service responded with: " +
	// status);
	// fail();
	// }
	//
	// response =
	// client.target("http://localhost:10000/services/blogger/user/2").request().get();
	// status = response.getStatus();
	// if (status != 200) {
	// _logger.error("Failed to fetch User; Web service responded with: " +
	// status);
	// fail();
	// }
	//
	// // Close the connection to the Web service.
	// response.close();
	// } finally {
	// // Release any connection resources.
	// client.close();
	// }
	// }

}
