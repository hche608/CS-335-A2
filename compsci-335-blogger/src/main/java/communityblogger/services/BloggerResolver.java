package communityblogger.services;

import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class BloggerResolver implements ContextResolver<JAXBContext>{
	private JAXBContext _context;
	
	// TO DO:
	// Add to the _classes array the classes whose object are to be marshalled/
	// unmarshalled to/from XML. Objects of these class will be exchanged by
	// clients and your Web service.
	private Class<?>[] _classes = {  };
	
	public BloggerResolver() {
		try {
			_context = JAXBContext.newInstance(_classes);
			
			// Initialise the context as required.
		} catch(JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public JAXBContext getContext(Class<?> type) {
		for(int i = 0; i < _classes.length; i++) {
			if(type.equals(_classes[i])) {
				return _context;
			}
		}
		return null;
	}
}
