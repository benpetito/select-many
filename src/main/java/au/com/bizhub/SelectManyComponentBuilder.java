package au.com.bizhub;

import java.util.List;

import javax.el.ValueExpression;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlSelectManyMenu;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.selectmanymenu.SelectManyMenu;
import org.skyve.impl.metadata.view.widget.bound.input.Combo;
import org.skyve.impl.web.faces.converters.select.SelectItemsBeanConverter;
import org.skyve.impl.web.faces.pipeline.component.ResponsiveComponentBuilder;
import org.skyve.impl.web.faces.pipeline.component.TabularComponentBuilder;
import org.skyve.metadata.view.TextOutput.Sanitisation;
import org.skyve.util.BeanValidator;
import org.skyve.util.Util;

/**
 * Custom component builder to use a selectMany PrimeFaces widget instead of a selectOne.
 * 
 * @author benpetito
 */
public class SelectManyComponentBuilder extends ResponsiveComponentBuilder {

	// names of the properties to refer to within the combo
	public static final String COMBO_TYPE_KEY = "comboType";

	@Override
	public EventSourceComponent combo(EventSourceComponent component, String dataWidgetVar, Combo combo,
			String formDisabledConditionName, String title, boolean required) {

		final String comboTypeProperty = combo.getProperties().get(COMBO_TYPE_KEY);
		if (StringUtils.isNotBlank(comboTypeProperty)) {
			Util.LOGGER.fine("Combo type: " + comboTypeProperty);

			if (comboTypeProperty.equalsIgnoreCase("selectmany")) {
				if (component != null) {
					return component;
				}

				String binding = combo.getBinding();
				HtmlSelectManyMenu result = selectManyMenu(dataWidgetVar,
						binding + "Values",
						title,
						required,
						combo.getDisabledConditionName(),
						formDisabledConditionName,
						null);

				UISelectItems i = selectItems(null, null, dataWidgetVar, binding, true);
				result.getChildren().add(i);

				return new EventSourceComponent(result, result);

			}

			// unknown combo type specified, call through to super
			return super.combo(component, dataWidgetVar, combo, formDisabledConditionName, title,
					required);
		}

		// normal combo behaviour, call through to super
		Util.LOGGER.fine("Combo type not specified");
		return super.combo(component, dataWidgetVar, combo, formDisabledConditionName, title,
				required);
	}

	/**
	 * Unchanged, copied from TabularComponentBuilder due to method privacy.
	 * 
	 * @see TabularComponentBuilder#selectItems()
	 */
	private UISelectItems selectItems(String moduleName,
			String documentName,
			String dataWidgetVar,
			String binding,
			boolean includeEmptyItems) {
		UISelectItems result = (UISelectItems) a.createComponent(UISelectItems.COMPONENT_TYPE);
		setId(result, null);
		String expression = null;
		ValueExpression valueExpression = null;
		if ((moduleName != null) && (documentName != null)) { // module and document, use FacesView.getSelectItems()
			expression = String.format("getSelectItems('%s','%s','%s',%s)",
					moduleName,
					documentName,
					binding,
					String.valueOf(includeEmptyItems));
			valueExpression = createValueExpressionFromFragment(managedBeanName, false, expression, false, null, List.class, false,
					Sanitisation.none);
		} else { // use the FacesView.currentBean.getSelectItems()
			expression = String.format("getSelectItems('%s',%s)", binding, String.valueOf(includeEmptyItems));
			if (dataWidgetVar != null) {
				valueExpression = createValueExpressionFromFragment(dataWidgetVar, true, expression, false, null, List.class, false,
						Sanitisation.none);
			} else {
				valueExpression = createValueExpressionFromFragment(expression, false, null, List.class, false, Sanitisation.none);
			}
		}
		result.setValueExpression("value", valueExpression);

		return result;
	}

	/**
	 * Same as SelectOneMenu, but returns the SelectManyMenu widget instead.
	 * 
	 * @see TabularComponentBuilder#selectOneMenu()
	 */
	private SelectManyMenu selectManyMenu(String dataWidgetVar, String binding, String title,
			boolean required, String disabled, String formDisabled, Integer pixelWidth) {

		SelectManyMenu result = (SelectManyMenu) input(SelectManyMenu.COMPONENT_TYPE,
				dataWidgetVar,
				binding,
				title,
				required,
				disabled,
				formDisabled);
		// Do not default pixel width to 100% as it causes renderering issues on the drop button on the end.
		// The control sets its width by default based on the font metrics of the drop-down values.
		setSizeAndTextAlignStyle(result, null, null, pixelWidth, null, null, null, null, null, null);
		result.setConverter(new SelectItemsBeanConverter());
		return result;
	}

	/**
	 * Unchanged, copied from TabularComponentBuilder due to method privacy.
	 * 
	 * @see TabularComponentBuilder#input()
	 */
	private UIInput input(String componentType,
			String dataWidgetVar,
			String binding,
			String title,
			boolean required,
			String disabled,
			String formDisabled) {
		UIInput result = (UIInput) a.createComponent(componentType);
		setId(result, null);
		if (binding != null) { // data table filter components don't set a binding
			if (dataWidgetVar != null) {
				result.setValueExpression("value",
						createValueExpressionFromFragment(dataWidgetVar, true, binding, true, null, Object.class, false,
								Sanitisation.none));
			} else {
				result.setValueExpression("value",
						createValueExpressionFromFragment(binding, true, null, Object.class, false, Sanitisation.none));
			}
		}
		if (title != null) {
			result.setValueExpression("title",
					ef.createValueExpression(elc, required ? title + " *" : title, String.class));
		}

		// Cannot utilise the faces required attributes as some requests need to ignore required-ness.
		// eg - triggered actions on widget events.
		// Setting required attribute to an expression worked server-side but the client-side message integration didn't.
		// result.setValueExpression("required", ef.createValueExpression(required ? "true" : "false", Boolean.class));
		// So we use the requiredMessage to perform the check ourselves based on clientValidation attribute
		if (required) {
			if (title == null) {
				result.setRequiredMessage(Util.i18n(BeanValidator.VALIDATION_REQUIRED_KEY, "Value"));
			} else {
				result.setRequiredMessage(Util.i18n(BeanValidator.VALIDATION_REQUIRED_KEY, title));
			}
		}
		setDisabled(result, disabled, formDisabled);
		return result;
	}
}
