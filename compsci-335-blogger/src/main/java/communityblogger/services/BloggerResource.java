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

import communityblogger.domain.BlogEntry;

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
	 * a communityblogger.services.dto.user object.
	 * 
	 * @param dtoUser
	 *            the User data included in the HTTP request body.
	 */
	@POST
	@Path("/users")
	@Consumes("application/xml")
	Response createUser(communityblogger.dto.User dtoUser);

	/**
	 * Returns a particular User. The returned User is represented by a
	 * communityblogger.services.dto.Uer object.
	 * 
	 * @param username
	 *            the unique identifier of the User.
	 * 
	 */
	@GET
	@Path("/users/{username}")
	@Produces("application/xml")
	communityblogger.dto.User getUser(@PathParam("username") String username);

	/**
	 * Adds a new Entry to the system. The state of the new Entry is described
	 * by a communityblogger.dto.BlogEntry object.
	 * 
	 * @param Entry
	 *            the Entry data included in the HTTP request body.
	 */
	@POST
	@Path("/blogEntries")
	@Consumes("application/xml")
	Response createEntry(BlogEntry Entry);

	/**
	 * Returns a particular Entry. The returned Entry is represented by a
	 * communityblogger.dto.BlogEntry object.
	 * 
	 * @param id
	 *            the unique identifier of the Entry.
	 * 
	 */
	@GET
	@Path("/blogEntries/{id}")
	@Produces("application/xml")
	BlogEntry getEntry(@PathParam("id") long id);
	//
	// /**
	// * Adds a new Comment to the system. The state of the new Comment is
	// described by
	// * a communityblogger.dto.Comment object.
	// *
	// * @param dtoComment
	// * the Comment data included in the HTTP request body.
	// */
	// @POST
	// @Path("/comments")
	// @Consumes("application/xml")
	// Response createComment(communityblogger.dto.Comment dtoEntry);
	//
	// /**
	// * Returns a particular Comment. The returned Comment is represented by a
	// * communityblogger.dto.Comment object.
	// *
	// * @param id
	// * the unique identifier of the Comment.
	// *
	// */
	// @GET
	// @Path("/blogEntries/{id}/comments/{id}")
	// @Produces("application/xml")
	// BlogEntry getComment(@PathParam("username") String username);
	//
	// /**
	// * Returns all particular Entries. The returned Entry is represented by a
	// * communityblogger.dto.BlogEntry object.
	// *
	// * @param id
	// * the unique identifier of the Entry.
	// *
	// */
	// @GET
	// @Path("/blogEntries")
	// @Produces("application/xml")
	// BlogEntry getEntries();
}
