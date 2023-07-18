package modules.selectMany.Appointment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.skyve.CORE;
import org.skyve.domain.types.DateOnly;
import org.skyve.persistence.Persistence;
import org.skyve.util.Time;

import modules.selectMany.domain.Appointment;
import modules.selectMany.domain.Appointment.Status;

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
	 * Creates a new appointment with the specified details.
	 * 
	 * @param title
	 */
	@SuppressWarnings("static-method")
	public AppointmentExtension createAppointment(LocalDateTime startDate, LocalDateTime endDate, String title) {
		AppointmentExtension appt = Appointment.newInstance();

		final DateOnly apptDate = Time.asDateOnly(startDate.toLocalDate());
		appt.setAppointmentDate(apptDate);
		appt.setLocation(title);

		// hardcoded required fields for example, these should be captured in the schedule dialog
		appt.setAppointmentTimeValues(Arrays.asList("09:00-09:15", "09:15-09:30"));
		appt.setStatus(Status.booked);

		return CORE.getPersistence().save(appt);
	}

	/**
	 * Retrieve the appointment with the specified identifier.
	 */
	@SuppressWarnings("static-method")
	public AppointmentExtension getAppointment(final String apptBizId) {
		return CORE.getPersistence().retrieve(Appointment.MODULE_NAME, Appointment.DOCUMENT_NAME, apptBizId);
	}

	/**
	 * Returns all appointments.
	 */
	public List<AppointmentExtension> getAllAppointments() {
		return persistence.newDocumentQuery(Appointment.MODULE_NAME, Appointment.DOCUMENT_NAME).beanResults();
	}

	/**
	 * Updates the appointment date by the number of days specified.
	 * @param apptBizId The bizId of the appointment to update
	 * @param dayDelta The number of days to update
	 */
	public void updateAppointmentDate(String apptBizId, int dayDelta) {
		final AppointmentExtension appointment = getAppointment(apptBizId);

		final DateOnly existingApptDate = appointment.getAppointmentDate();
		final DateOnly newApptDate = new DateOnly(Date.from(existingApptDate.toInstant().plus(dayDelta, ChronoUnit.DAYS)));

		appointment.setAppointmentDate(newApptDate);
		CORE.getPersistence().save(appointment);
	}
}
