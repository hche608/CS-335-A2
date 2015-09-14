package communityblogger.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import communityblogger.domain.BlogEntry;
import communityblogger.domain.User;
import communityblogger.dto.Comment;

/**
 * Implementation of the BloggerResource interface.
 *
 */
public class BloggerResourceImpl implements BloggerResource {

	// Setup a Logger.
	private static Logger _logger = LoggerFactory.getLogger(BloggerResourceImpl.class);

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
		_logger.debug("Read User: " + dtoUser);
		User _user = UserMapper.toDomainModel(dtoUser);
		_users.put(_user.getUsername(), _user);

		_logger.debug("Created user: " + _user);
		return Response.created(URI.create("/blogger/users/" + _user.getUsername())).build();
	}

	/**
	 * Returns a particular User. The returned User is represented by a
	 * communityblogger.dto.User object.
	 * 
	 * @param username
	 *            the unique identifier of the User.
	 * 
	 */
	public communityblogger.dto.User getUser(@PathParam("username") String username) {
		// Lookup the User within the in-memory data structure.
		
		final User _user = _users.get(username);
		_logger.debug("Lookup for user: " + _user);
		final String _username = _user.getUsername();
		if (_username == null) {
			// Return a HTTP 404 response if the specified User isn't found.
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		// Convert the full User to a short User.
		communityblogger.dto.User dtoUser = UserMapper.toDto(_user);
		return dtoUser;
	}

	@Override
	public Response createEntry(BlogEntry entry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlogEntry getEntry(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response createComment(Comment dtoEntry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlogEntry getComment(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlogEntry getEntries() {
		// TODO Auto-generated method stub
		return null;
	}
}
