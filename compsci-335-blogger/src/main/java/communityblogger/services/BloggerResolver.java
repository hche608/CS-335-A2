package communityblogger.services;

import communityblogger.domain.BlogEntry;
import communityblogger.domain.Comment;
import communityblogger.domain.User;

import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class BloggerResolver implements ContextResolver<JAXBContext> {
	private JAXBContext _context;

	// TO DO:
	// Add to the _classes array the classes whose object are to be marshalled/
	// unmarshalled to/from XML. Objects of these class will be exchanged by
	// clients and your Web service.
	private Class<?>[] _classes = { BlogEntry.class, Comment.class, User.class };

	public BloggerResolver() {
		try {
			// The JAXV Context should be able to marshal and unmarshal the
			// specified classes.
			_context = JAXBContext.newInstance(_classes);

			// Initialise the context as required.
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public JAXBContext getContext(Class<?> type) {
		for (int i = 0; i < _classes.length; i++) {
			if (type.equals(_classes[i])) {
				return _context;
			}
		}
		return null;
	}
}
