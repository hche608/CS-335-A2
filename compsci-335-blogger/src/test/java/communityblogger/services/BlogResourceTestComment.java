package communityblogger.services;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import communityblogger.domain.BlogEntry;
import communityblogger.dto.Comment;
import communityblogger.dto.User;

public class BlogResourceTestComment {

	private Logger _logger = LoggerFactory
			.getLogger(BlogResourceTestComment.class);

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

		User _dto_user = new User("hw", "hello", "world");

		_logger.info("Creating a new User ...");
		response = _client.target(WEB_SERVICE_URI + "/users").request()
				.post(Entity.xml(_dto_user));
		if (response.getStatus() != 201) {
			_logger.error("Failed to create User; Web service responded with: "
					+ response.getStatus());
			fail("Failed to create new User");
		}

		String location = response.getLocation().toString();
		_logger.info("URI for new User: " + location);

		response.close();

		_dto_user = new User("ch", "Chen", "Hao");

		_logger.info("Creating a new User ...");
		response = _client.target(WEB_SERVICE_URI + "/users").request()
				.post(Entity.xml(_dto_user));
		if (response.getStatus() != 201) {
			_logger.error("Failed to create User; Web service responded with: "
					+ response.getStatus());
			fail("Failed to create new User");
		}

		location = response.getLocation().toString();
		_logger.info("URI for new User: " + location);

		response.close();

		Cookie myCookie = new Cookie("username", "ch");
		BlogEntry _entry = new BlogEntry("hello world!");
		_logger.info("Creating a new Entry without time ...");

		response = _client.target(WEB_SERVICE_URI + "/blogEntries").request()
				.cookie(myCookie).post(Entity.xml(_entry));
		if (response.getStatus() != 201) {
			_logger.error("Failed to create Entry; Web service responded with: "
					+ response.getStatus());
			fail("Failed to create new Entry");
		}

		location = response.getLocation().toString();
		_logger.info("URI for new Entry: " + location);

		response.close();

		// Case2
		Set<String> _keywords = new HashSet<String>();
		_keywords.add("This");
		_keywords.add("is");
		_keywords.add("a");
		_keywords.add("key");
		_keywords.add("word");
		_entry = new BlogEntry("This is a key word!!!", _keywords);
		_logger.info("Creating a new Entry without time ...");

		response = _client.target(WEB_SERVICE_URI + "/blogEntries").request()
				.cookie(myCookie).post(Entity.xml(_entry));
		if (response.getStatus() != 201) {
			_logger.error("Failed to create Entry; Web service responded with: "
					+ response.getStatus());
			fail("Failed to create new Entry");
		}

		location = response.getLocation().toString();
		_logger.info("URI for new Entry: " + location);

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
	public void addComments() {
		Cookie myCookie = new Cookie("username", "ch");
		Comment _comment1 = new Comment("This is comment1", DateTime.now());
		
		_logger.info("Creating a new Comment ...");

		Response response = _client.target(WEB_SERVICE_URI + "/blogEntries/2/comments")
				.request().cookie(myCookie).post(Entity.xml(_comment1));
		if (response.getStatus() != 201) {
			_logger.error("Failed to create Comment; Web service responded with: "
					+ response.getStatus());
			fail("Failed to create new Comment");
		}

		String location = response.getLocation().toString();
		_logger.info("URI for new Comment: " + location);
		response.close();
		
		// Query the Web service for the new User.
//		Set<Comment> commentFromService = Set<_client.target(location).request()
//				.accept("application/xml").get(Comment.class)>;

		// The original local User object (_user) should have a value equal
		// to that of the User object representing User that is later
		// queried from the Web service. The only exception is the value
		// returned by getId(), because the Web service assigns this when it
		// creates a User.
		//assertEquals(_comment1.getContent(), commentFromService.getContent());
		
		// Case 2
		Comment _comment2 = new Comment("This is comment2", DateTime.now());
		
		_logger.info("Creating a new Comment ...");

		response = _client.target(WEB_SERVICE_URI + "/blogEntries/2/comments")
				.request().cookie(myCookie).post(Entity.xml(_comment2));
		if (response.getStatus() != 201) {
			_logger.error("Failed to create Comment; Web service responded with: "
					+ response.getStatus());
			fail("Failed to create new Comment");
		}

		location = response.getLocation().toString();
		_logger.info("URI for new Comment: " + location);
		response.close();
		
		// Query the Web service for the new User.
//		commentFromService = _client.target(location).request()
//				.accept("application/xml").get(Comment.class);

		// The original local User object (_user) should have a value equal
		// to that of the User object representing User that is later
		// queried from the Web service. The only exception is the value
		// returned by getId(), because the Web service assigns this when it
		// creates a User.
		//assertEquals(_comment1.getContent(), commentFromService.getContent());
		
	}
}
