package au.com.bizhub.faces;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.skyve.impl.web.faces.beans.FacesView;
import org.skyve.util.Time;

import modules.selectMany.Appointment.AppointmentExtension;
import modules.selectMany.Appointment.AppointmentService;

@ManagedBean(name = "scheduleView")
@ViewScoped
public class ScheduleView extends FacesView<modules.selectMany.domain.ScheduleView> {

	private static final long serialVersionUID = 2303644269039081613L;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm-HH:mm");
	private ScheduleModel eventModel;

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
							.build());
				}
			}
		};
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}
}
