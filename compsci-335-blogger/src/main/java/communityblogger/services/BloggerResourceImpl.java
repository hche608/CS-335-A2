package communityblogger.services;


import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import communityblogger.domain.BlogEntry;
import communityblogger.domain.User;

/**
 * Implementation of the BloggerResource interface.
 *
 */
public class BloggerResourceImpl implements BloggerResource {

	/*
	 * Possible data structures to store the domain model objects.
	 * _users is a map whose key is username. Each User is assumed to have a
	 * unique username.
	 * _blogEntries is a map whose key is the ID of a BlogEntry. Each BlogEntry
	 * is assumed to have a unique ID.
	 * _idCounter is a thread-safe counter, which can be used to assign unique
	 * IDs to blogEntry objects as they are created. 
	 */
	private Map<String, User> _users;
	private Map<Long, BlogEntry> _blogEntries;
	private AtomicLong _idCounter;


	public BloggerResourceImpl() {
		// TO DO:
		// Initialise instance variables.
	}

	public void initialiseContent() {
		// TO DO:
		// (Re)-initialise data structures so that the Web service's state is
		// the same same as when the Web service was initially created.	
	}
	
	// TO DO:
	// Implement BloggerResource methods for the service contract.
}
