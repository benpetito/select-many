package modules.selectMany.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import modules.selectMany.Appointment.AppointmentExtension;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.domain.types.Enumeration;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.metadata.model.document.Bizlet.DomainValue;
import org.skyve.util.Util;

/**
 * Appointment
 * 
 * @depend - - - Status
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public abstract class Appointment extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "selectMany";

	/** @hidden */
	public static final String DOCUMENT_NAME = "Appointment";

	/** @hidden */
	public static final String locationPropertyName = "location";

	/** @hidden */
	public static final String appointmentTimePropertyName = "appointmentTime";

	/** @hidden */
	public static final String statusPropertyName = "status";

	/**
	 * Status
	 **/
	@XmlEnum
	public static enum Status implements Enumeration {
		available("Available", "Available"),
		booked("Booked", "Booked");

		private String code;
		private String description;

		/** @hidden */
		private DomainValue domainValue;

		/** @hidden */
		private static List<DomainValue> domainValues = Stream.of(values()).map(Status::toDomainValue).collect(Collectors.toUnmodifiableList());

		private Status(String code, String description) {
			this.code = code;
			this.description = description;
			this.domainValue = new DomainValue(code, description);
		}

		@Override
		public String toCode() {
			return code;
		}

		@Override
		public String toLocalisedDescription() {
			return Util.i18n(description);
		}

		@Override
		public DomainValue toDomainValue() {
			return domainValue;
		}

		public static Status fromCode(String code) {
			Status result = null;

			for (Status value : values()) {
				if (value.code.equals(code)) {
					result = value;
					break;
				}
			}

			return result;
		}

		public static Status fromLocalisedDescription(String description) {
			Status result = null;

			for (Status value : values()) {
				if (value.toLocalisedDescription().equals(description)) {
					result = value;
					break;
				}
			}

			return result;
		}

		public static List<DomainValue> toDomainValues() {
			return domainValues;
		}
	}

	/**
	 * Appointment Location
	 **/
	private String location;

	/**
	 * Appointment Time
	 * <br/>
	 * Select 1 (or more) times for your appointment
	 **/
	private String appointmentTime;

	/**
	 * Status
	 **/
	private Status status;

	@Override
	@XmlTransient
	public String getBizModule() {
		return Appointment.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return Appointment.DOCUMENT_NAME;
	}

	public static AppointmentExtension newInstance() {
		try {
			return CORE.getUser().getCustomer().getModule(MODULE_NAME).getDocument(CORE.getUser().getCustomer(), DOCUMENT_NAME).newInstance(CORE.getUser());
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new DomainException(e);
		}
	}

	@Override
	@XmlTransient
	public String getBizKey() {
		try {
			return org.skyve.util.Binder.formatMessage("Appointment", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof Appointment) && 
					this.getBizId().equals(((Appointment) o).getBizId()));
	}

	/**
	 * {@link #location} accessor.
	 * @return	The value.
	 **/
	public String getLocation() {
		return location;
	}

	/**
	 * {@link #location} mutator.
	 * @param location	The new value.
	 **/
	@XmlElement
	public void setLocation(String location) {
		preset(locationPropertyName, location);
		this.location = location;
	}

	/**
	 * {@link #appointmentTime} accessor.
	 * @return	The value.
	 **/
	public String getAppointmentTime() {
		return appointmentTime;
	}

	/**
	 * {@link #appointmentTime} mutator.
	 * @param appointmentTime	The new value.
	 **/
	@XmlElement
	public void setAppointmentTime(String appointmentTime) {
		preset(appointmentTimePropertyName, appointmentTime);
		this.appointmentTime = appointmentTime;
	}

	/**
	 * {@link #status} accessor.
	 * @return	The value.
	 **/
	public Status getStatus() {
		return status;
	}

	/**
	 * {@link #status} mutator.
	 * @param status	The new value.
	 **/
	@XmlElement
	public void setStatus(Status status) {
		preset(statusPropertyName, status);
		this.status = status;
	}
}
