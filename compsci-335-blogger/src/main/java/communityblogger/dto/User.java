package communityblogger.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * Use the Apache Commons library for implementing equals() and hasCode(). 
 * Apache Commons provides utility classes that simplify the implementation of 
 * these methods, such they meet the requirements set out in Javadoc 
 * documentation for class Object.
 * 
 * Apache Commons is a third-party library. This project's POM file necessarily
 * includes a dependency on the Apache Commons library.
 * 
 * See https://commons.apache.org/proper/commons-lang/javadocs/api-3.4/ for the
 * Javadoc.
 */
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class to represent users in the Community Blogger Web service. A User object
 * stores the following data:
 * 
 * - user-name, a unique username for the User. It is the caller's
 * responsibility to ensure that any username value given to User's constructor
 * is unique.
 * 
 * - last-name, the User's last name.
 * 
 * - first-name, the User's first name.
 * 
 * - blog entries, a set of BlogEntry objects representing blog entries that
 * have been posted by this User.
 * 
 * - comments, a set of Comment objects that have been made by this User.
 * 
 * Class User is not thread-safe. It is the class user's responsibility to
 * ensure that any concurrent access to User objects is managed appropriately.
 * 
 * @author Ian Warren
 *
 */
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

	@XmlAttribute(name = "username")
	private String _username;

	@XmlElement(name = "last-name")
	private String _lastname;

	@XmlElement(name = "first-name")
	private String _firstname;

	/**
	 * Creates a User.
	 * 
	 * @param username
	 *            - the user's unique username. It is the caller's
	 *            responsibility to ensure that the supplied username is unique
	 *            (i.e. that it isn't already taken by an existing User).
	 * @param lastname
	 *            - the user's last name.
	 * @param firstname
	 *            - the user's first name.
	 * 
	 */
	public User() {
	}

	public User(String username, String lastname, String firstname) {

		_username = username;
		_lastname = lastname;
		_firstname = firstname;

	}

	/**
	 * Returns this User's username.
	 * 
	 */
	public String getUsername() {
		return _username;
	}

	/**
	 * Returns this User's last name.
	 * 
	 */
	public String getLastname() {
		return _lastname;
	}

	/**
	 * Returns this User's first name.
	 * 
	 */
	public String getFirstname() {
		return _firstname;
	}

	/**
	 * Retursn true if this User object is equal in value to the method
	 * argument.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User))
			return false;
		if (obj == this)
			return true;

		User rhs = (User) obj;
		return new EqualsBuilder().append(_username, rhs._username)
				.append(_lastname, rhs._lastname)
				.append(_firstname, rhs._firstname).isEquals();
	}

	/**
	 * Returns a hashcode for this User object.
	 * 
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(_username).append(_lastname)
				.append(_firstname).toHashCode();
	}

	/**
	 * Returns a String representation of this User object.
	 * 
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("[User:");
		buffer.append(" username=");
		if (_username != null)
			buffer.append(_username);
		buffer.append(", lastname=");
		if (_lastname != null)
			buffer.append(_lastname);
		buffer.append(", firstname=");
		if (_firstname != null)
			buffer.append(_firstname);
		buffer.append("]");
		return buffer.toString();
	}

}
