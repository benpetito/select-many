package au.com.bizhub;

import org.skyve.impl.web.faces.pipeline.component.ComponentBuilderChain;
import org.skyve.impl.web.faces.pipeline.component.ResponsiveComponentBuilder;

public class CustomComponentBiulderChain extends ComponentBuilderChain {

	public CustomComponentBiulderChain() {
		super(new SelectManyComponentBuilder(), new ResponsiveComponentBuilder());
	}
}
