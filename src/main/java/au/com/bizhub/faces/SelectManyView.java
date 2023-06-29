package au.com.bizhub.faces;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.skyve.impl.web.faces.beans.FacesView;

import modules.selectMany.Appointment.AppointmentExtension;

@ManagedBean(name = "selectManyView")
@ViewScoped
public class SelectManyView extends FacesView<AppointmentExtension> {

	private static final long serialVersionUID = -3453444734224201581L;

	/**
	 * Gets the list of all available appointment times. This is a hardcoded
	 * list in this example, but could issue a query.
	 */
	@SuppressWarnings("static-method")
	public List<String> getAllAppointmentTimes() {
		// create list of appointment times as domain values to populate the drop down
		return Arrays.asList("09:00-09:15", "09:15-09:30", "09:30-09:45", "09:45-10:00")
				.stream()
				.collect(Collectors.toList());
	}

	/**
	 * Gets the list of saved appointment times for this appointment
	 * (comma separated list of strings), and converts it to a list for the Faces
	 * select many component.
	 */
	public List<String> getSelectedAppointmentTimes() {
		if (getBean() != null) {
			return getBean().getAppointmentTimeValues();
		}
		return Collections.emptyList();
	}

	/**
	 * Sets the saved appointment time which is converted from a list
	 * of Strings to a comma separated String in AppointmentExtension.
	 * 
	 * @param selectedTimes The time(s) entered by the user
	 */
	public void setSelectedAppointmentTimes(List<String> selectedTimes) {
		if (getBean() != null) {
			getBean().setAppointmentTimeValues(selectedTimes);
		}
	}
}
