package au.com.bizhub.faces;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.schedule.Schedule;
import org.primefaces.component.schedule.ScheduleRenderer;

public class SkyveScheduleRenderer extends 	ScheduleRenderer{
@Override
public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
	// TODO Auto-generated method stub
	super.encodeBegin(context, component);
}
@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
		super.encodeEnd(context, component);
	}
@Override
	protected void encodeMarkup(FacesContext context, Schedule schedule) throws IOException {
		// TODO Auto-generated method stub
		super.encodeMarkup(context, schedule);
	}
@Override
	protected void renderChild(FacesContext context, UIComponent child) throws IOException {
		// TODO Auto-generated method stub
		super.renderChild(context, child);
	}
@Override
	protected void renderChildren(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
		super.renderChildren(context, component);
	}
}

