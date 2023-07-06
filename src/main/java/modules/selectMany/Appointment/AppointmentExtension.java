package modules.selectMany.Appointment;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import modules.selectMany.domain.Appointment;

public class AppointmentExtension extends Appointment {

	private static final long serialVersionUID = 7285492504198222751L;

	/**
	 * Returns the end time of the last chosen time block as a LocalTime,
	 * or null if no times have been selected.
	 */
	public LocalTime getAppointmentEndTime() {
		List<String> appointmentTimes = getAppointmentTimeValues();
		if (appointmentTimes != null && appointmentTimes.size() > 0) {
			// get the end time of the last time in the list
			String[] times = appointmentTimes.get(appointmentTimes.size() - 1).split("-");
			String endTime = times[1];

			// Parse the start time into a LocalTime
			LocalTime end = LocalTime.parse(endTime);
			return end;
		}

		return null;
	}

	/**
	 * Returns the start time of the first chosen time block as a LocalTime,
	 * or null if no times have been selected.
	 */
	public LocalTime getAppointmentStartTime() {
		List<String> appointmentTimes = getAppointmentTimeValues();
		if (appointmentTimes != null && appointmentTimes.size() > 0) {
			// get the start time of the first time in the list
			String[] times = appointmentTimes.get(0).split("-");
			String startTime = times[0];

			// Parse the start time into a LocalTime
			LocalTime start = LocalTime.parse(startTime);
			return start;
		}

		return null;
	}

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
		super.setAppointmentTime(appointmentTimes.stream()
				.collect(Collectors.joining(", ")));
	}
}
