package communityblogger.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import communityblogger.domain.User;

/**
 * JAXB XML adapter to convert between Joda LocalTime instances and Strings.
 * LocalTime objects are marshalled as Strings, and unmarshalled back into
 * LocalTime instances.
 * 
 */
public class UserAdapter extends XmlAdapter<String, User> {

	@Override
	public User unmarshal(String username) throws Exception {
		if (username == null) {
			return null;
		}
		return null;
	}

	@Override
	public String marshal(User _user) throws Exception {
		if (_user == null) {
			return null;
		}
		return _user.getUsername();
	}
}
