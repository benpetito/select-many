package modules.selectMany.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import modules.selectMany.Appointment.AppointmentExtension;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.domain.types.DateOnly;
import org.skyve.domain.types.DateTime;
import org.skyve.domain.types.Enumeration;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.impl.domain.ChangeTrackingArrayList;
import org.skyve.impl.domain.types.jaxb.DateOnlyMapper;
import org.skyve.impl.domain.types.jaxb.DateTimeMapper;
import org.skyve.metadata.model.document.Bizlet.DomainValue;
import org.skyve.util.ExpressionEvaluator;
import org.skyve.util.Util;

/**
 * Appointment
 * 
 * @depend - - - Status
 * @navhas n services 0..n Service
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
	public static final String appointmentDatePropertyName = "appointmentDate";

	/** @hidden */
	public static final String appointmentTimePropertyName = "appointmentTime";

	/** @hidden */
	public static final String statusPropertyName = "status";

	/** @hidden */
	public static final String servicesPropertyName = "services";

	/** @hidden */
	public static final String appointmentDateTimePropertyName = "appointmentDateTime";

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
	 * Appointment Date
	 **/
	private DateOnly appointmentDate = (DateOnly) ExpressionEvaluator.evaluate("{DATE}", this);

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

	/**
	 * Services Required
	 * <br/>
	 * Select 1 (or more) services for your appointment
	 **/
	private List<Service> services = new ChangeTrackingArrayList<>("services", this);

	/**
	 * Date/Time
	 **/
	private DateTime appointmentDateTime;

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
	 * {@link #appointmentDate} accessor.
	 * @return	The value.
	 **/
	public DateOnly getAppointmentDate() {
		return appointmentDate;
	}

	/**
	 * {@link #appointmentDate} mutator.
	 * @param appointmentDate	The new value.
	 **/
	@XmlElement
	@XmlSchemaType(name = "date")
	@XmlJavaTypeAdapter(DateOnlyMapper.class)
	public void setAppointmentDate(DateOnly appointmentDate) {
		preset(appointmentDatePropertyName, appointmentDate);
		this.appointmentDate = appointmentDate;
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

	/**
	 * {@link #services} accessor.
	 * @return	The value.
	 **/
	@XmlElement
	public List<Service> getServices() {
		return services;
	}

	/**
	 * {@link #services} accessor.
	 * @param bizId	The bizId of the element in the list.
	 * @return	The value of the element in the list.
	 **/
	public Service getServicesElementById(String bizId) {
		return getElementById(services, bizId);
	}

	/**
	 * {@link #services} mutator.
	 * @param bizId	The bizId of the element in the list.
	 * @param element	The new value of the element in the list.
	 **/
	public void setServicesElementById(String bizId, Service element) {
		setElementById(services, element);
	}

	/**
	 * {@link #services} add.
	 * @param element	The element to add.
	 **/
	public boolean addServicesElement(Service element) {
		return services.add(element);
	}

	/**
	 * {@link #services} add.
	 * @param index	The index in the list to add the element to.
	 * @param element	The element to add.
	 **/
	public void addServicesElement(int index, Service element) {
		services.add(index, element);
	}

	/**
	 * {@link #services} remove.
	 * @param element	The element to remove.
	 **/
	public boolean removeServicesElement(Service element) {
		return services.remove(element);
	}

	/**
	 * {@link #services} remove.
	 * @param index	The index in the list to remove the element from.
	 **/
	public Service removeServicesElement(int index) {
		return services.remove(index);
	}

	/**
	 * {@link #appointmentDateTime} accessor.
	 * @return	The value.
	 **/
	public DateTime getAppointmentDateTime() {
		return appointmentDateTime;
	}

	/**
	 * {@link #appointmentDateTime} mutator.
	 * @param appointmentDateTime	The new value.
	 **/
	@XmlElement
	@XmlSchemaType(name = "dateTime")
	@XmlJavaTypeAdapter(DateTimeMapper.class)
	public void setAppointmentDateTime(DateTime appointmentDateTime) {
		preset(appointmentDateTimePropertyName, appointmentDateTime);
		this.appointmentDateTime = appointmentDateTime;
	}
}
