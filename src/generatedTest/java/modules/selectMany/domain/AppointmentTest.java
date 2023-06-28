package modules.selectMany.domain;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractDomainTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractDomainTest} to create your own tests for this document.
 */
public class AppointmentTest extends AbstractDomainTest<Appointment> {

	@Override
	protected Appointment getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(Appointment.MODULE_NAME, Appointment.DOCUMENT_NAME);
	}
}