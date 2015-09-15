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

public class BlogResourceTestUser {

	private Logger _logger = LoggerFactory
			.getLogger(BlogResourceTestUser.class);

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
		User _dto_user = new User("hw", "Hello", "world");

		_logger.info("Creating a new dto_User ...");
		Response response = _client.target(WEB_SERVICE_URI + "/users")
				.request().post(Entity.xml(_dto_user));
		if (response.getStatus() != 201) {
			_logger.error("Failed to create User; Web service responded with: "
					+ response.getStatus());
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

		_logger.info("Creating a new xml_User ...");

		// Create a XML representation for a new User.
		String xml = "<user username=\"ch\">" + "<first-name>Hao</first-name>"
				+ "<last-name>Chen</last-name>" + "</user>";

		// Send a HTTP POST message, with a message body containing the XML,
		// to the Web service.
		Response response = _client.target(WEB_SERVICE_URI + "/users")
				.request().post(Entity.xml(xml));

		// Expect a HTTP 201 "Created" response from the Web service.
		int status = response.getStatus();
		if (status != 201) {
			_logger.error("Failed to create User; Web service responded with: "
					+ status);
			fail();
		}

		// Extract location header from the HTTP response message. This
		// should
		// give the URI for the newly created User.
		String location = response.getLocation().toString();
		_logger.info("URI for new User: " + location);

		// Close the connection to the Web service.
		response.close();

		// Query the Web service for the new User.
		User userFromService = _client.target(location).request()
				.accept("application/xml").get(User.class);

		// The original local User object (_user) should have a value equal
		// to that of the User object representing User that is later
		// queried from the Web service. The only exception is the value
		// returned by getId(), because the Web service assigns this when it
		// creates a User.
		assertEquals("ch", userFromService.getUsername());
		assertEquals("Chen", userFromService.getLastname());
		assertEquals("Hao", userFromService.getFirstname());

	}
}
