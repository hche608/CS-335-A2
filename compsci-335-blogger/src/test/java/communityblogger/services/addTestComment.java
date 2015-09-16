package communityblogger.services;

import static org.junit.Assert.fail;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import communityblogger.dto.Comment;

public class addTestComment {

	private Logger _logger = LoggerFactory.getLogger(addTestComment.class);

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
	 * Tests that the Web service can create a new User.
	 */
	@Test
	public void addComments() {
		Cookie myCookie = new Cookie("username", "ch");
		Comment _comment1 = new Comment("This is comment1", DateTime.now());

		_logger.info("Creating a new Comment ...");

		Response response = _client
				.target(WEB_SERVICE_URI + "/blogEntries/2/comments").request()
				.cookie(myCookie).post(Entity.xml(_comment1));
		if (response.getStatus() != 201) {
			_logger.error("Failed to create Comment; Web service responded with: "
					+ response.getStatus());
			fail("Failed to create new Comment");
		}

		String location = response.getLocation().toString();
		_logger.info("URI for new Comment: " + location);
		response.close();

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

	}
}
