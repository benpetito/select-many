package au.com.bizhub.faces;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.skyve.CORE;
import org.skyve.impl.web.faces.beans.FacesView;

import modules.selectMany.Appointment.AppointmentExtension;
import modules.selectMany.domain.Service;

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
	 * Returns the list of all available services from the database.
	 */
	@SuppressWarnings("static-method")
	public List<Service> getAllServices() {
		return CORE.getPersistence().newDocumentQuery(Service.MODULE_NAME, Service.DOCUMENT_NAME).beanResults();
	}

	public List<String> getAllServicesString() {
		return getAllServices()
				.stream()
				.map(s -> s.getName())
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
	 * Gets the list of saved Services for this appointment and converts
	 * them to a list of Strings.
	 */
	public List<String> getSelectedServices() {
		// The Faces SelectCheckboxMenu component should be able to handle
		// the conversion for display from the Skyve record, but using
		// a basic string to get this working
		if (getBean() != null) {
			return getBean().getServices().stream()
					.map(s -> s.getName())
					.collect(Collectors.toList());
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

	/**
	 * Sets the saved services onto the Appointment.
	 * 
	 * @param selectedServices The services selected by the user
	 */
	public void setSelectedServices(List<String> selected) {
		if (getBean() != null) {
			// this could be done more efficiently, this is just to demonstrate the process
			// of updating the underlying Skyve bean
			List<Service> selectedServices = getAllServices().stream()
					.filter(s -> selected.contains(s.getName()))
					.collect(Collectors.toList());
			
			getBean().getServices().clear();
			getBean().getServices().addAll(selectedServices);
		}
	}
}
