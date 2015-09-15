@XmlJavaTypeAdapters({
		@XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class),
		@XmlJavaTypeAdapter(type = LocalTime.class, value = LocalTimeAdapter.class),
		@XmlJavaTypeAdapter(type = DateTime.class, value = DateTimeAdapter.class) })
package communityblogger.domain;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import communityblogger.jaxb.DateTimeAdapter;
import communityblogger.jaxb.LocalDateAdapter;
import communityblogger.jaxb.LocalTimeAdapter;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;