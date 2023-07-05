package modules.selectMany.domain;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractDomainTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractDomainTest} to create your own tests for this document.
 */
public class ServiceTest extends AbstractDomainTest<Service> {

	@Override
	protected Service getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(Service.MODULE_NAME, Service.DOCUMENT_NAME);
	}
}