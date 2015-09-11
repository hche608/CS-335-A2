package communityblogger.test;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlogResourceTest {

	private Logger _logger = LoggerFactory.getLogger(BlogResourceTest.class);

	@Test
	public void testNewUser() {
		// Use ClientBuilder to create a new client that can be used to create
		// connections to the Web service.
		Client client = ClientBuilder.newClient();
		try {
			_logger.info("Creating a new Parolee ...");

			// Create a XML representation for a new Parolee.
			String xml = "<user username=\"ml\">"
					+ "<first-name>Mark</first-name>"
					+ "<last-name>Lundy</last-name>"
					+ "</user>";

			// Send a HTTP POST message, with a message body containing the XML,
			// to the Web service.
			Response response = client.target("http://localhost:10000/services/blogger/user").request()
					.post(Entity.xml(xml));

			// Expect a HTTP 201 "Created" response from the Web service.
			int status = response.getStatus();
			if (status != 201) {
				_logger.error("Failed to create User; Web service responded with: " + status);
				fail();
			}

			// Extract location header from the HTTP response message. This
			// should
			// give the URI for the newly created Parolee.
			String location = response.getLocation().toString();
			_logger.info("URI for new Parolee: " + location);

			// Close the connection to the Web service.
			response.close();
		} finally {
			// Release any connection resources.
			client.close();
		}
	}

}
