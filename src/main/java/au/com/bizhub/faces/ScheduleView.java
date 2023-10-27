package au.com.bizhub.faces;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.schedule.Schedule;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.schedule.ScheduleEntryMoveEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.skyve.impl.web.faces.beans.FacesView;
import org.skyve.util.Util;

import modules.selectMany.Appointment.AppointmentExtension;
import modules.selectMany.MedAppointment.MedAppointmentService;
import modules.selectMany.domain.MedAppointment;

@ManagedBean(name = "scheduleView")
@ViewScoped
public class ScheduleView extends FacesView<modules.selectMany.domain.ScheduleView> {

	private static final long serialVersionUID = 2303644269039081613L;

	@Inject
	private transient MedAppointmentService medAppointmentService;
	private ScheduleEvent<?> event = new DefaultScheduleEvent<>();
	private int currentScheduleId;
	private List<SkyveScheduleModel> eventModels= new ArrayList<SkyveScheduleModel>();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm-HH:mm");
	
	private LocalDate bookingDate = LocalDate.now();
	
	
	@PostConstruct
	public void init() {
		
		for(int i=1;i<20;i++) {
			SkyveScheduleModel eventModel = new SkyveScheduleModel() {};
			eventModel.setId(i);
			eventModel.setDoctorName("Doctor in ENT "+i);
		eventModels.add(eventModel);
		}
		
	}

	

	public List<SkyveScheduleModel> getEventModels() {

		return eventModels;
	}
	public void onPreviousClick()
	{
		this.bookingDate= this.bookingDate.minusDays(1);
		for (SkyveScheduleModel skyveScheduleModel : eventModels) {
			skyveScheduleModel.loadEvents(LocalDateTime.now().minus(1,ChronoUnit.DAYS),LocalDateTime.now().minus(1,ChronoUnit.DAYS));
		}
	}
	public void onNextClick()
	{
		this.bookingDate= this.bookingDate.plusDays(1);
		for (SkyveScheduleModel skyveScheduleModel : eventModels) {
			
			skyveScheduleModel.loadEvents(LocalDateTime.now().plus(1,ChronoUnit.DAYS),LocalDateTime.now().plus(1,ChronoUnit.DAYS));
		}
		
	}
	

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
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
			MedAppointment newAppt = medAppointmentService.createAppointment(event.getStartDate(), event.getEndDate(),
					event.getTitle(),currentScheduleId);
			DefaultScheduleEvent newEvent = (DefaultScheduleEvent) event;
			newEvent.setData(newAppt.getBizId());
			newEvent.setStartDate(event.getStartDate());
			newEvent.setEndDate(event.getEndDate());
			newEvent.setStyleClass("booked");
			eventModels.stream().filter(t -> t.getId()==currentScheduleId).findFirst().get().addEvent(newEvent);
		} else {
			eventModels.stream().filter(t -> t.getId()==currentScheduleId).findFirst().get().updateEvent(event);
		}

		// reset the selected event to a new one for the next interaction
		event = new DefaultScheduleEvent<>();
	}
	public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
		event = DefaultScheduleEvent.builder()
				.startDate(selectEvent.getObject())
				.endDate(selectEvent.getObject().plusHours(1))
				.build();
		ScheduleModel z = ((Schedule)selectEvent.getSource()).getValue();
		this.currentScheduleId = ((SkyveScheduleModel)z).getId();
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
		//medAppointmentService.updateAppointmentDate(apptBizId, moveEvent.getDayDelta());
	}

	public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
		event = selectEvent.getObject();
	}
	public void onEventDelete() {
        String eventId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("eventId");
        if (event != null) {
            
        }
    }

	public void setEvent(ScheduleEvent<?> event) {
		this.event = event;
	}

	public ScheduleEvent<?> getEvent() {
		return event;
	}



	public int getCurrentScheduleId() {
		return currentScheduleId;
	}



	public void setCurrentScheduleId(int currentScheduleId) {
		this.currentScheduleId = currentScheduleId;
	}

}
