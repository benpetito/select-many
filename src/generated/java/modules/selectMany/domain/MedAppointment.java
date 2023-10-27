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
import modules.selectMany.MedAppointment.MedAppointmentExtension;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.domain.types.DateTime;
import org.skyve.domain.types.Enumeration;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.impl.domain.types.jaxb.DateTimeMapper;
import org.skyve.metadata.model.document.Bizlet.DomainValue;
import org.skyve.util.Util;

/**
 * MedAppointment
 * 
 * @depend - - - Status
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public abstract class MedAppointment extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "selectMany";

	/** @hidden */
	public static final String DOCUMENT_NAME = "MedAppointment";

	/** @hidden */
	public static final String titlePropertyName = "title";

	/** @hidden */
	public static final String startDateTimePropertyName = "startDateTime";

	/** @hidden */
	public static final String endDateTimePropertyName = "endDateTime";

	/** @hidden */
	public static final String statusPropertyName = "status";

	/** @hidden */
	public static final String doctorIdPropertyName = "doctorId";

	/**
	 * Status
	 **/
	@XmlEnum
	public static enum Status implements Enumeration {
		available("Available", "Available"),
		booked("Booked", "Booked"),
		cancelled("Cancelled", "Cancelled");

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
	 * Title
	 **/
	private String title;

	/**
	 * Start Date Time
	 **/
	private DateTime startDateTime;

	/**
	 * End Date Time
	 **/
	private DateTime endDateTime;

	/**
	 * Status
	 **/
	private Status status;

	/**
	 * Doctor Id
	 **/
	private Integer doctorId;

	@Override
	@XmlTransient
	public String getBizModule() {
		return MedAppointment.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return MedAppointment.DOCUMENT_NAME;
	}

	public static MedAppointmentExtension newInstance() {
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
			return org.skyve.util.Binder.formatMessage("MedAppointment", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof MedAppointment) && 
					this.getBizId().equals(((MedAppointment) o).getBizId()));
	}

	/**
	 * {@link #title} accessor.
	 * @return	The value.
	 **/
	public String getTitle() {
		return title;
	}

	/**
	 * {@link #title} mutator.
	 * @param title	The new value.
	 **/
	@XmlElement
	public void setTitle(String title) {
		preset(titlePropertyName, title);
		this.title = title;
	}

	/**
	 * {@link #startDateTime} accessor.
	 * @return	The value.
	 **/
	public DateTime getStartDateTime() {
		return startDateTime;
	}

	/**
	 * {@link #startDateTime} mutator.
	 * @param startDateTime	The new value.
	 **/
	@XmlElement
	@XmlSchemaType(name = "dateTime")
	@XmlJavaTypeAdapter(DateTimeMapper.class)
	public void setStartDateTime(DateTime startDateTime) {
		preset(startDateTimePropertyName, startDateTime);
		this.startDateTime = startDateTime;
	}

	/**
	 * {@link #endDateTime} accessor.
	 * @return	The value.
	 **/
	public DateTime getEndDateTime() {
		return endDateTime;
	}

	/**
	 * {@link #endDateTime} mutator.
	 * @param endDateTime	The new value.
	 **/
	@XmlElement
	@XmlSchemaType(name = "dateTime")
	@XmlJavaTypeAdapter(DateTimeMapper.class)
	public void setEndDateTime(DateTime endDateTime) {
		preset(endDateTimePropertyName, endDateTime);
		this.endDateTime = endDateTime;
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
	 * {@link #doctorId} accessor.
	 * @return	The value.
	 **/
	public Integer getDoctorId() {
		return doctorId;
	}

	/**
	 * {@link #doctorId} mutator.
	 * @param doctorId	The new value.
	 **/
	@XmlElement
	public void setDoctorId(Integer doctorId) {
		preset(doctorIdPropertyName, doctorId);
		this.doctorId = doctorId;
	}
}
