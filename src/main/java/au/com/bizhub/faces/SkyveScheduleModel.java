package au.com.bizhub.faces;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleDisplayMode;

import modules.selectMany.MedAppointment.MedAppointmentExtension;
import modules.selectMany.MedAppointment.MedAppointmentService;

public class SkyveScheduleModel extends LazyScheduleModel{

	private int id;
	private String doctorName;
	

	@Inject
	private transient MedAppointmentService medAppointmentService;
	
	private static final long serialVersionUID = 5741991251716959466L;

	@Override
	public void loadEvents(LocalDateTime start, LocalDateTime end) {
			// load appointments from the database
			if(medAppointmentService == null)
				medAppointmentService = new MedAppointmentService();
			List<MedAppointmentExtension> appointments = medAppointmentService.getAllAppointments(id);
			for (MedAppointmentExtension appt : appointments) {
			
				LocalDateTime startDateTime = appt.getStartDateTime().toLocalDateTime();
				LocalDateTime endDateTime = appt.getEndDateTime().toLocalDateTime();				
				
				addEvent(DefaultScheduleEvent.builder()
						.title(appt.getTitle())
						.startDate(startDateTime)
						.endDate(endDateTime)
						.data(appt.getBizId()) // the bizId of the Skyve record
						.display(ScheduleDisplayMode.BACKGROUND)
		                .backgroundColor("green")
						.build());
				
			}
			
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	
	

	public void loadRandomEvents() {
		
		this.loadEvents(LocalDateTime.now(), LocalDateTime.now());
		
	}
	
}
