package modules.selectMany.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import modules.selectMany.Appointment.AppointmentExtension;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.impl.domain.AbstractPersistentBean;

/**
 * Appointment
 * 
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
	public static final String appointmentTimePropertyName = "appointmentTime";

	/**
	 * Appointment Time
	 * <br/>
	 * Select 1 (or more) times for your appointment
	 **/
	private String appointmentTime;

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
}
