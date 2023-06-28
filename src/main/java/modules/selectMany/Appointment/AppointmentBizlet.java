package modules.selectMany.Appointment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.skyve.metadata.model.document.Bizlet;

import modules.selectMany.domain.Appointment;

public class AppointmentBizlet extends Bizlet<Appointment> {

	@Override
	public List<DomainValue> getVariantDomainValues(String attributeName) throws Exception {
		
		if(Appointment.appointmentTimePropertyName.equals(attributeName)) {
			// create list of appointment times as domain values to populate the drop down
			return Arrays.asList("09:00-09:15", "09:15-09:30", "09:30-09:45", "09:45-10:00").stream()
					.map(s -> new DomainValue(s))
					.collect(Collectors.toList());
		}
		
		return super.getVariantDomainValues(attributeName);
	}

}
