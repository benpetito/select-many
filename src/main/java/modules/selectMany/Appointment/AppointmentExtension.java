package modules.selectMany.Appointment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import modules.selectMany.domain.Appointment;

public class AppointmentExtension extends Appointment {

	private static final long serialVersionUID = 7285492504198222751L;

	public List<String> getAppointmentTimeValues() {
		if (super.getAppointmentTime() != null) {
			// split on comma
			String[] times = super.getAppointmentTime().split(",");
			return Arrays.asList(times).stream()
					.map(s -> s.trim())
					.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public void setAppointmentTimeValues(List<String> appointmentTimes) {
		if(appointmentTimes != null) {
			super.setAppointmentTime(appointmentTimes.stream()
					.collect(Collectors.joining(", ")));
		}
	}
}
