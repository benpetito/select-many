package modules.selectMany.MedAppointment;

import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.skyve.CORE;
import org.skyve.domain.types.DateTime;
import org.skyve.persistence.DocumentQuery;
import org.skyve.persistence.Persistence;

import modules.selectMany.domain.MedAppointment;

/**
 * This class acts as a service layer to encapsulate domain logic.
 *
 * Add this line to classes that wish to use it: @Inject private transient AppointmentService service;
 */
@Default
public class MedAppointmentService {

	/**
	 * Creates a new appointment with the specified details.
	 * 
	 * @param title
	 * @param doctorId 
	 */
	@SuppressWarnings("static-method")
	public MedAppointment createAppointment(LocalDateTime startDateTime, LocalDateTime endDateTime, String title, int doctorId) {
		
		MedAppointment appt = MedAppointment.newInstance();
		
		appt.setStartDateTime(new DateTime(startDateTime));
		appt.setEndDateTime(new DateTime(endDateTime));
		appt.setTitle(title);
		appt.setDoctorId(doctorId);
		return CORE.getPersistence().save(appt);
	}

	/**
	 * Retrieve the appointment with the specified identifier.
	 */
	@SuppressWarnings("static-method")
	public MedAppointmentExtension getAppointment(final String apptBizId) {
		return CORE.getPersistence().retrieve(MedAppointment.MODULE_NAME, MedAppointment.DOCUMENT_NAME, apptBizId);
	}

	/**	
	 * Returns all appointments.
	 */
	public List<MedAppointmentExtension> getAllAppointments(int doctorId) {
		
		DocumentQuery query = CORE.getPersistence().newDocumentQuery(MedAppointment.MODULE_NAME, MedAppointment.DOCUMENT_NAME);
		query.getFilter().addEquals(MedAppointment.doctorIdPropertyName, doctorId);
		return query.beanResults();
	}

	/**
	 * Updates the appointment date by the number of days specified.
	 * @param apptBizId The bizId of the appointment to update
	 * @param dayDelta The number of days to update
	 */
	public void updateAppointmentDate(String apptBizId,LocalDateTime startDateTime,LocalDateTime endDateTime) {
		final MedAppointmentExtension appointment = getAppointment(apptBizId);

//		final DateOnly existingApptDate = appointment.getAppointmentDate();
//		final DateOnly newApptDate = new DateOnly(Date.from(existingApptDate.toInstant().plus(dayDelta, ChronoUnit.DAYS)));

		appointment.setStartDateTime(new DateTime(startDateTime));
		appointment.setEndDateTime(new DateTime(endDateTime));
		CORE.getPersistence().save(appointment);
	}
}
