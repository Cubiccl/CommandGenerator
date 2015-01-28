package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.util.Map;

import commandGenerator.arguments.objects.Attribute;
import commandGenerator.arguments.objects.AttributeType;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.gui.helper.components.CComboBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class AttributeSelectionPanel extends HelperPanel
{

	private CEntry entryAmount;
	private CComboBox comboboxAttribute;
	private LangComboBox comboboxOperation;

	public AttributeSelectionPanel()
	{
		super(CGConstants.PANELID_OPTIONS, "GUI:add.attribute", 600, 400);

		entryAmount = new CEntry(CGConstants.DATAID_NONE, "GUI:attribute.amount");
		entryAmount.setTextField("1");

		comboboxAttribute = new CComboBox(CGConstants.DATAID_NONE, "GUI:attribute.select", Registerer.getObjectList(CGConstants.OBJECT_ATTRIBUTE), null);
		comboboxOperation = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:attribute.operation", 3);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(entryAmount, gbc);
		gbc.gridy++;
		add(comboboxAttribute, gbc);
		gbc.gridy++;
		add(comboboxOperation, gbc);

	}

	@Override
	public void updateLang()
	{}

	public Attribute getAttribute()
	{
		String amount = entryAmount.getText();
		try
		{
			@SuppressWarnings("unused")
			double test = Double.parseDouble(amount);
		} catch (Exception e)
		{
			DisplayHelper.showWarning("WARNING:number");
			return null;
		}
		return new Attribute((AttributeType) comboboxAttribute.getValue(), Double.parseDouble(amount), comboboxOperation.getSelectedIndex());
	}

	public void setupFrom(Map<String, Object> data)
	{
		Attribute attribute = (Attribute) data.get(getPanelId());
		comboboxOperation.setSelectedIndex(attribute.getOperation());
		entryAmount.setTextField(Double.toString(attribute.getAmount()));
		comboboxAttribute.setSelected(attribute.getType());
	}

}
