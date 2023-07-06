package modules.selectMany.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.impl.domain.AbstractTransientBean;

/**
 * Schedule View
 * <br/>
 * Non-persistent Skyve view to show a PrimeFaces schedule component. Uses 
		a custom xhtml page via the router.
 * 
 * @stereotype "transient"
 */
@XmlType
@XmlRootElement
public class ScheduleView extends AbstractTransientBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "selectMany";

	/** @hidden */
	public static final String DOCUMENT_NAME = "ScheduleView";

	@Override
	@XmlTransient
	public String getBizModule() {
		return ScheduleView.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return ScheduleView.DOCUMENT_NAME;
	}

	public static ScheduleView newInstance() {
		try {
			return CORE.getUser().getCustomer().getModule(MODULE_NAME).getDocument(CORE.getUser().getCustomer(), DOCUMENT_NAME).newInstance(CORE.getUser());
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new DomainException(e);
		}
	}

	@Override
	@XmlTransient
	public String getBizKey() {
		try {
			return org.skyve.util.Binder.formatMessage("ScheduleView", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof ScheduleView) && 
					this.getBizId().equals(((ScheduleView) o).getBizId()));
	}
}
