package communityblogger.services;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

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
	@Produces(MediaType.APPLICATION_XML)
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
	@Consumes(MediaType.APPLICATION_XML)
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
	@Produces(MediaType.APPLICATION_XML)
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
	@Consumes(MediaType.APPLICATION_XML)
	Response createEntry(@CookieParam("username") Cookie userCookie, BlogEntry Entry);

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
	@Produces(MediaType.APPLICATION_XML)
	BlogEntry getEntry(@PathParam("id") long id);

	/**
	 * Adds a new Comment to the system. The state of the new Comment is
	 * described by a communityblogger.dto.Comment object.
	 *
	 * @param dtoComment
	 *            the Comment data included in the HTTP request body.
	 */
	@POST
	@Path("/blogEntries/{id}/comments")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	Response createComment(@CookieParam("username") Cookie userCookie, @PathParam("id") long id,
			communityblogger.dto.Comment dtoComment) throws InterruptedException;

	/**
	 * Returns a particular Comment. The returned Comment is represented by a
	 * communityblogger.dto.Comment object.
	 *
	 * @param id
	 *            the unique identifier of the Comment.
	 *
	 */
	@GET
	@Path("/blogEntries/{id}/comments")
	@Produces(MediaType.APPLICATION_XML)
	Set<communityblogger.dto.Comment> getComments(@PathParam("id") long id);

	/**
	 * Returns all particular Entries. The returned Entry is represented by a
	 * Set<communityblogger.dto.BlogEntry> object.
	 *
	 * @param index
	 *            the start index of the Entry.
	 * @param offset
	 *            the offset.
	 *
	 *            URI Pattern : �blogEntries/query?index=1&offset=10�
	 */
	@GET
	@Path("/blogEntries/query")
	@Produces(MediaType.APPLICATION_XML)
	List<BlogEntry> getEntries(@DefaultValue("1") @QueryParam("index") int index,
			@DefaultValue("10") @QueryParam("offset") int offset);

	/**
	 * Returns a particular Comment. The returned Comment is represented by a
	 * Set<communityblogger.dto.Comment> object.
	 *
	 * @param id
	 *            the unique identifier of the Comment.
	 *
	 */
	@GET
	@Path("/blogEntries/{id}/follow")
	@Produces(MediaType.APPLICATION_XML)
	void getFollow(@CookieParam("lastreportid") Cookie userCookie, @Suspended final AsyncResponse asyncResponse,
			@PathParam("id") final long id) throws InterruptedException;
}
