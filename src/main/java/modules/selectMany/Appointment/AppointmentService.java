package modules.selectMany.Appointment;

import java.util.List;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.skyve.persistence.Persistence;

import modules.selectMany.domain.Appointment;

/**
 * This class acts as a service layer to encapsulate domain logic.
 *
 * Add this line to classes that wish to use it: @Inject private transient AppointmentService service;
 */
@Default
public class AppointmentService {

	@Inject
	private transient Persistence persistence;

	/**
	 * Returns all appointments.
	 */
	public List<AppointmentExtension> getAllAppointments() {
		return persistence.newDocumentQuery(Appointment.MODULE_NAME, Appointment.DOCUMENT_NAME).beanResults();
	}
}
