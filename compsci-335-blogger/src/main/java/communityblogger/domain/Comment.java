package communityblogger.domain;

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

/*
 * Use the Joda Date/Time library for working with Dates.
 * 
 * Similarly to Apache Commons, Joda is not part of the regular JDK and so a 
 * dependency on this library is also included in the project's POM file.
 * 
 * See http://www.joda.org/joda-time/apidocs/index.html for the Javadoc.
 */
import org.joda.time.DateTime;

/**
 * Class to represent a comment made by a User on a BlogEntry. A Comment object
 * holds the following data:
 * 
 * - timestamp, which stores the time when the Comment was made.
 * 
 * - content, the comment's text.
 * 
 * - author, the User who made the comment.
 * 
 * Class Comment is not thread-safe. It is the class user's responsibility to
 * ensure that any concurrent access to Comment objects is managed
 * appropriately.
 * 
 * @author Ian Warren
 *
 */
public class Comment implements Comparable<Comment> {
	private DateTime _timestamp;
	private String _content;
	private User _author;

	/**
	 * Creates a Comment object.
	 * 
	 * @param content
	 *            the text for the Comment.
	 * 
	 * @param timestamp
	 *            the time when the Comment was created.
	 * 
	 */
	public Comment(String content, DateTime timestamp) {
		_content = content;
		_timestamp = timestamp;
	}

	/**
	 * Returns the time at which this Comment was made.
	 * 
	 */
	public DateTime getTimePosted() {
		return _timestamp;
	}

	/**
	 * Returns this Comment's content.
	 * 
	 */
	public String getContent() {
		return _content;
	}

	/**
	 * Returns this Comment's author.
	 * 
	 */
	public User getAuthor() {
		return _author;
	}

	/**
	 * Sets the time at which this Comment was made.
	 * 
	 * @param timestamp
	 *            the creation date/time.
	 * 
	 */
	public void setTimestamp(DateTime timestamp) {
		_timestamp = timestamp;
	}

	/**
	 * Sets the User who has authored this Comment.
	 * 
	 * Note that this method has package visibility, so can only be called by
	 * classes within the same package. This is a form of encapsulation that
	 * prevents unintended use of this method. Class User calls this method to
	 * establish a bidirectional link between a User and a BlogEntry; class User
	 * calls this method in its addBlogEntry() method.
	 * 
	 * @param user
	 *            this Comment's author.
	 * 
	 */
	void setAuthor(User user) {
		_author = user;
	}

	/**
	 * Return true if this Comment object is equal in value to the method
	 * argument.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Comment))
			return false;
		if (obj == this)
			return true;

		Comment rhs = (Comment) obj;
		return new EqualsBuilder().append(_content, rhs._content)
				.append(_timestamp, rhs._timestamp)
				.append(_author, rhs._author).isEquals();
	}

	/**
	 * Returns a hashcode for this User object.
	 * 
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(_content).append(_timestamp)
				.append(_author).toHashCode();
	}

	/**
	 * Returns a String representation of this User object.
	 * 
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("[Comment:");

		buffer.append(" content=\"");
		if (_content != null) {
			buffer.append(_content);
			buffer.append("\"");
		} else {
			buffer.append("null");
		}

		buffer.append(", timestamp=");
		if (_timestamp != null) {
			buffer.append(_timestamp);
		} else {
			buffer.append("null");
		}

		buffer.append(", author=");
		if (_author != null) {
			buffer.append(_author.getUsername());
		} else {
			buffer.append("null");
		}

		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Compares this Comment with the method parameter.
	 * 
	 * @returns a negative integer if this Comment was made earlier than the
	 *          other Comment; zero if this Comment was made at the same time as
	 *          the other Comment, or a positive integer if this Comment was
	 *          made later than the other Comment.
	 * 
	 */
	public int compareTo(Comment other) {
		return _timestamp.compareTo(other._timestamp);
	}
}
