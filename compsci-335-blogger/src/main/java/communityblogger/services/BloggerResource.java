package communityblogger.services;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

// TO DO:
// Configure the relative URI path for this resource.
@Path("/blogger")
public interface BloggerResource {

	// TO DO:
	// Define one method for each of the operations in the Community Blogger
	// Web service contract. In each case, use appropriate JAX-RS annotations
	// to specify the URI pattern, media type and HTTP method.
	//
	// The service contract comprises the 8 operations:
	// - Create user
	// - Retrieve user
	// - Create blog entry
	// - Retrieve blog entry
	// - Create comment
	// - Retrieve comments
	// - Retrieve blog entries
	// - Follow blog entry

	/**
	 * Useful operation to initialise the state of the Web service. This
	 * operation can be invoked prior to executing each unit test.
	 */
	@PUT
	@Produces("application/xml")
	void initialiseContent();

	/**
	 * Adds a new User to the system. The state of the new User is described by
	 * a nz.ac.auckland.parolee.dto.Parolee object.
	 * 
	 * @param dtoUser
	 *            the User data included in the HTTP request body.
	 */
	@POST
	@Path("/user")
	@Consumes("application/xml")
	Response createUser(communityblogger.dto.User dtoUser);

	/**
	 * Returns a particular Parolee. The returned Parolee is represented by a
	 * nz.ac.auckland.parolee.dto.Parolee object.
	 * 
	 * @param id
	 *            the unique identifier of the Parolee.
	 * 
	 */
	@GET
	@Path("/users/{username}")
	@Produces("application/xml")
	communityblogger.dto.User getUser(@PathParam("username") String username);
}
