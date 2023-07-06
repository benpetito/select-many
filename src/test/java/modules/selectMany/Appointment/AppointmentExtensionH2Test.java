package modules.selectMany.Appointment;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modules.selectMany.domain.Appointment;
import util.AbstractH2TestForJUnit5;

public class AppointmentExtensionH2Test extends AbstractH2TestForJUnit5 {

	private AppointmentExtension bean;

	@BeforeEach
	public void setup() throws Exception {
		bean = Appointment.newInstance();
	}

	@Test
	public void testGetAppointmentStartTimeNoTimesReturnsNull() {
		// call the method under test
		LocalTime result = bean.getAppointmentStartTime();

		// verify the result
		assertThat(result, is(nullValue()));
	}

	@Test
	public void testGetAppointmentStartTimeValidTimeReturnsTime() {
		// setup the test data
		bean.setAppointmentTimeValues(Arrays.asList("09:00-09:15"));

		// call the method under test
		LocalTime result = bean.getAppointmentStartTime();

		// verify the result
		assertThat(result, is(LocalTime.of(9, 0)));
	}

	@Test
	public void testGetAppointmentEndTimeNoTimesReturnsNull() {
		// call the method under test
		LocalTime result = bean.getAppointmentEndTime();

		// verify the result
		assertThat(result, is(nullValue()));
	}

	@Test
	public void testGetAppointmentEndTimeValidTimeReturnsTime() {
		// setup the test data
		bean.setAppointmentTimeValues(Arrays.asList("13:00-13:15", "14:15-14:30"));

		// call the method under test
		LocalTime result = bean.getAppointmentEndTime();

		// verify the result
		assertThat(result, is(LocalTime.of(14, 30)));
	}

}
