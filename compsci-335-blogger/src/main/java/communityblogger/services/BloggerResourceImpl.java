package communityblogger.services;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import communityblogger.domain.BlogEntry;
import communityblogger.domain.Comment;
import communityblogger.domain.User;

import org.joda.time.DateTime;

/**
 * Implementation of the BloggerResource interface.
 *
 */
public class BloggerResourceImpl implements BloggerResource {

	// Setup a Logger.
	private static Logger _logger = LoggerFactory
			.getLogger(BloggerResourceImpl.class);

	/*
	 * Possible data structures to store the domain model objects. _users is a
	 * map whose key is username. Each User is assumed to have a unique
	 * username. _blogEntries is a map whose key is the ID of a BlogEntry. Each
	 * BlogEntry is assumed to have a unique ID. _idCounter is a thread-safe
	 * counter, which can be used to assign unique IDs to blogEntry objects as
	 * they are created.
	 */
	private Map<String, User> _users;
	private Map<Long, BlogEntry> _blogEntries;
	private AtomicLong _idCounter;

	public BloggerResourceImpl() {
		// TO DO:
		// Initialise instance variables.
		initialiseContent();
	}

	public void initialiseContent() {
		// TO DO:
		// (Re)-initialise data structures so that the Web service's state is
		// the same same as when the Web service was initially created.
		_users = new ConcurrentHashMap<String, User>();
		_blogEntries = new ConcurrentHashMap<Long, BlogEntry>();
		_idCounter = new AtomicLong();
		_logger.debug("Server reloaded.");
	}

	/**
	 * Adds a new User to the system. The state of the new User is described by
	 * a communityblogger.dto.User object.
	 * 
	 * @param dtoUser
	 *            the User data included in the HTTP request body.
	 */
	public Response createUser(communityblogger.dto.User dtoUser) {

		_logger.debug("....................................................................................");
		_logger.debug("Read User: " + dtoUser);
		final String dtoUsername = dtoUser.getUsername();
		// Check if the username existed in database
		if (_users.get(dtoUsername) != null) {
			// Return a HTTP 409 response if the specified User is found.
			throw new WebApplicationException(Response.Status.CONFLICT);
		}
		User _user = UserMapper.toDomainModel(dtoUser);
		_users.put(_user.getUsername(), _user);
		_logger.debug("Created user: " + _user);
		return Response.created(
				URI.create("/blogger/users/" + _user.getUsername())).build();
	}

	/**
	 * Returns a particular User. The returned User is represented by a
	 * communityblogger.dto.User object.
	 * 
	 * @param username
	 *            the unique identifier of the User.
	 * 
	 */
	public communityblogger.dto.User getUser(String username) {
		// Lookup the User within the in-memory data structure.
		_logger.debug("....................................................................................");
		final User _user = _users.get(username);
		if (_user == null) {
			// Return a HTTP 404 response if the specified User isn't found.
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		_logger.debug("Lookup for user: " + _user);
		// Convert the full User to a short User.
		communityblogger.dto.User dtoUser = UserMapper.toDto(_user);
		return dtoUser;
	}

	public Response createEntry(String username, BlogEntry _entry) {
		// Lookup the User within the in-memory data structure.
		_logger.debug("....................................................................................");
		User _user = _users.get(username);
		_logger.debug("Lookup for user: " + _user);
		final String _username = _user.getUsername();
		if (_username == null) {
			// Return a HTTP 412 response if the precondition failed.
			throw new WebApplicationException(
					Response.Status.PRECONDITION_FAILED);
		}
		_logger.debug("Read Entry: " + _entry);
		_entry.setId(_idCounter.incrementAndGet());
		_user.addBlogEntry(_entry);
		_entry.setTimePosted(DateTime.now());
		_blogEntries.put(_entry.getId(), _entry);
		_logger.debug("Created entry: " + _entry);
		return Response.created(
				URI.create("/blogger/blogEntries/" + _entry.getId())).build();

	}

	public BlogEntry getEntry(long id) {
		// Lookup the Entry within the in-memory data structure.
		_logger.debug("....................................................................................");
		_logger.debug("Lookup for the Entry with id: " + id);
		final BlogEntry _entry = _blogEntries.get(id);
		if (_entry == null) {
			// Return a HTTP 404 response if the specified Entry isn't found.
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return _entry;
	}

	public Response createComment(String username, long id,
			communityblogger.dto.Comment dtoComment) {
		// Lookup the User within the in-memory data structure.
		_logger.debug("....................................................................................");
		User _user = _users.get(username);
		_logger.debug("Lookup for user: " + _user);
		final String _username = _user.getUsername();
		if (_username == null) {
			// Return a HTTP 412 response if the precondition failed.
			throw new WebApplicationException(
					Response.Status.PRECONDITION_FAILED);
		}

		// Lookup the Entry with in the in-memory data structure.
		_logger.debug("Lookup for the Entry with id: " + id);

		BlogEntry _entry = _blogEntries.get(id);
		if (_entry == null) {
			// Return a HTTP 404 response if the specified Entry isn't found.
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		// Convert data to object
		Comment _comment = CommentMapper.toDomainModel(dtoComment);
		// set time-stamp
		_comment.setTimestamp(DateTime.now());

		_logger.debug("Read Comment: " + _comment);
		// add comment to entry and gives it a author
		_entry.addComment(_comment);
		_user.addComment(_comment);

		_logger.debug("Created Comment: " + _comment);
		return Response.created(
				URI.create("/blogger/blogEntries/" + id + "/comments")).build();

	}

	public Set<communityblogger.dto.Comment> getComments(long id) {
		// Lookup the Entry within the in-memory data structure.

		_logger.debug("Lookup for the Entry with id: " + id);

		final BlogEntry _entry = _blogEntries.get(id);
		if (_entry == null) {
			// Return a HTTP 404 response if the specified User isn't found.
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		// Convert the full User to a short User.
		Set<communityblogger.dto.Comment> dtoComments = CommentMapper
				.toDto(_entry.getComments());
		return dtoComments;
	}

	// @Override
	// public BlogEntry getEntries() {
	// // TODO Auto-generated method stub
	// return null;
	// }
}
