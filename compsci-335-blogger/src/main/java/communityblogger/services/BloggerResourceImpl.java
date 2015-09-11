package communityblogger.services;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import communityblogger.domain.BlogEntry;
import communityblogger.domain.User;
import communityblogger.domain.Comment;

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
        reloadData();
	}
    
    @Override
	public void initialiseContent() {
		// TO DO:
		// (Re)-initialise data structures so that the Web service's state is
		// the same same as when the Web service was initially created.
    	_users = new ConcurrentHashMap<String, User>();
		_blogEntries = new ConcurrentHashMap<Long, BlogEntry>();
		_idCounter = new AtomicLong();
    	//reloadData();    
	}
    
    protected void reloadData(){
    	_users = new ConcurrentHashMap<String, User>();
		_blogEntries = new ConcurrentHashMap<Long, BlogEntry>();
		_idCounter = new AtomicLong();
        
        // === Initialise User #1
        User _user = new User("hche608","Chen","Hao");
        _logger.info("Default User" + _user.toString());
    }

    @Override
	public Response create_new_user(InputStream is) {
		User _user = readUser(is);
		_users.put(_user.getUsername(), _user);
		_logger.info("Processing HTTP POST request -- create new user:" + _user.toString());
		return Response.created(URI.create("/blogger/users/" + _user.getUsername()))
				.build();
	}

    @Override
	public Response create_new_blog_entry(InputStream is) {
		_logger.debug("Processing HTTP POST request -- create new blogEntry");
		BlogEntry _blogEntry = readEntry(is);
		_blogEntry.setId(_idCounter.incrementAndGet());
		_blogEntries.put(_blogEntry.getId(), _blogEntry);
		return Response.created(
				URI.create("/blogEntries/" + _blogEntry.getId())).build();
	}

    @Override
	public Response create_new_comment(InputStream is) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
	public StreamingOutput retrieve_user(@PathParam("username") long username) {
		// TODO Auto-generated method stub
		// Lookup the Parolee within the in-memory data structure.
		final User _user = _users.get(username);
		final String _username = _user.getUsername();
		if (_username == null) {
			// Return a HTTP 404 response if the specified Parolee isn't found.
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		// Return a StreamingOuput instance that the JAX-RS implementation will
		// use set the body of the HTTP response message.
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException,
					WebApplicationException {
				outputUser(outputStream, _user);
			}
		};
	}

    @Override
	public StreamingOutput retrieve_blog_entry(@PathParam("username") long username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public StreamingOutput retrieve_comments(long username) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
	public StreamingOutput retrieve_blog_entries(InputStream is) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String follow_blog_entry(InputStream is) {
		// TODO Auto-generated method stub
		return null;
	}

	// TO DO:
	// Implement BloggerResource methods for the service contract.

	// Helper method to read an XML representation of a User, and return a
	// corresponding User object. This method uses the org.w3c API for
	// parsing XML. The details aren't important, and later we'll use an
	// automated approach rather than having to do this by hand. Currently this
	// is a minimal Web service and so we'll parse the XML by hand.
	protected User readUser(InputStream is) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			String _username = "", _lastname = "", _firstname = "";
			if (root.getAttribute("username") != null
					&& !root.getAttribute("username").trim().equals(""));
				_username = root.getAttribute("username");
			NodeList nodes = root.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				if (element.getTagName().equals("last-name")) {
					_lastname = element.getTextContent();
				} else if (element.getTagName().equals("first-name")) {
					_firstname = element.getTextContent();
				}
			}
			return new User(_username, _lastname, _firstname);
		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}

	}

	// Helper method to read an XML representation of a BlogEntry, and return a
	// corresponding BlogEntry object. This method uses the org.w3c API for
	// parsing XML. The details aren't important, and later we'll use an
	// automated approach rather than having to do this by hand. Currently this
	// is a minimal Web service and so we'll parse the XML by hand.
	protected BlogEntry readEntry(InputStream is){
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			
			DateTimeFormatter formatter = DateTimeFormat.forPattern("h:mma 'on' dd/MM/yyyy");
			//String current_time = LocalDateTime.now().format(formatter);

			
			
			String _username = "", _lastname = "", _firstname = "";
			if (root.getAttribute("user") != null
					&& !root.getAttribute("user").trim().equals(""))
				;
			NodeList nodes = root.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				if (element.getTagName().equals("username")) {
					_username = element.getTextContent();
				} else if (element.getTagName().equals("last-name")) {
					_lastname = element.getTextContent();
				} else if (element.getTagName().equals("first-name")) {
					_firstname = element.getTextContent();
				}
			}
			return null;
		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}

	protected Comment readComment(InputStream is) {
		return null;
	}

	// Helper method to generate an XML representation of a particular User.
	protected void outputUser(OutputStream os, User user) throws IOException {
		PrintStream writer = new PrintStream(os);
		writer.println("<user username=\"" + user.getUsername() + "\">");
		writer.println("   <first-name>" + user.getFirstname()
				+ "</first-name>");
		writer.println("   <last-name>" + user.getLastname() + "</last-name>");
		writer.println("</user>");
	}

}
