package au.com.bizhub.faces;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.schedule.ScheduleEntryMoveEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.skyve.impl.web.faces.beans.FacesView;
import org.skyve.util.Time;
import org.skyve.util.Util;

import modules.selectMany.Appointment.AppointmentExtension;
import modules.selectMany.Appointment.AppointmentService;

@ManagedBean(name = "scheduleView")
@ViewScoped
public class ScheduleView extends FacesView<modules.selectMany.domain.ScheduleView> {

	private static final long serialVersionUID = 2303644269039081613L;

	private ScheduleEvent<?> event = new DefaultScheduleEvent<>();
	private ScheduleModel eventModel;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm-HH:mm");

	@Inject
	private transient AppointmentService appointmentService;

	@PostConstruct
	public void init() {
		eventModel = new LazyScheduleModel() {

			private static final long serialVersionUID = 5741991251716959466L;

			@Override
			public void loadEvents(LocalDateTime start, LocalDateTime end) {
				// load appointments from the database
				List<AppointmentExtension> appointments = appointmentService.getAllAppointments();
				for (AppointmentExtension appt : appointments) {

					LocalDateTime startDateTime = LocalDateTime.of(Time.asLocalDate(appt.getAppointmentDate()),
							appt.getAppointmentStartTime());
					LocalDateTime endDateTime = LocalDateTime.of(Time.asLocalDate(appt.getAppointmentDate()),
							appt.getAppointmentEndTime());

					addEvent(DefaultScheduleEvent.builder()
							.title(appt.getLocation())
							.startDate(startDateTime)
							.endDate(endDateTime)
							.data(appt.getBizId()) // the bizId of the Skyve record
							.build());
				}
			}
		};
	}

	public void addEvent() {
		Util.LOGGER.info("Add new event");
		if (event.isAllDay()) {
			// see https://github.com/primefaces/primefaces/issues/1164
			if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
				event.setEndDate(event.getEndDate().plusDays(1));
			}
		}

		if (event.getId() == null) {

			// create a corresponding appointment in the Skyve database when an event is added to the underlying model
			AppointmentExtension newAppt = appointmentService.createAppointment(event.getStartDate(), event.getEndDate(),
					event.getTitle());
			DefaultScheduleEvent newEvent = (DefaultScheduleEvent) event;
			newEvent.setData(newAppt.getBizId());

			eventModel.addEvent(newEvent);
		} else {
			eventModel.updateEvent(event);
		}

		// reset the selected event to a new one for the next interaction
		event = new DefaultScheduleEvent<>();
	}

	public ScheduleEvent<?> getEvent() {
		return event;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
		event = DefaultScheduleEvent.builder()
				.startDate(selectEvent.getObject())
				.endDate(selectEvent.getObject().plusHours(1))
				.build();
	}

	/**
	 * Update the Skyve {@link AppointmentExtension} when the appointment
	 * is moved on the schedule.
	 */
	public void onEventMove(ScheduleEntryMoveEvent moveEvent) {
		Util.LOGGER.info(String.format("Event %s moved by days %s %s", moveEvent.getScheduleEvent().getId(),
				Integer.valueOf(moveEvent.getDayDelta()),
				moveEvent.getScheduleEvent().getStartDate()));

		final String apptBizId = (String) moveEvent.getScheduleEvent().getData();
		appointmentService.updateAppointmentDate(apptBizId, moveEvent.getDayDelta());
	}

	public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
		event = selectEvent.getObject();
	}

	public void setEvent(ScheduleEvent<?> event) {
		this.event = event;
	}
}
