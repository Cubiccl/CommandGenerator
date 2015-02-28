package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.util.HashMap;
import java.util.Map;

import commandGenerator.arguments.objects.Attribute;
import commandGenerator.arguments.objects.AttributeType;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.button.LoadButton;
import commandGenerator.gui.helper.components.button.SaveButton;
import commandGenerator.gui.helper.components.combobox.CComboBox;
import commandGenerator.gui.helper.components.combobox.LangComboBox;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class AttributeSelectionPanel extends HelperPanel implements ISave
{

	private CButton buttonSave, buttonLoad;
	private CComboBox comboboxAttribute;
	private LangComboBox comboboxOperation;
	private CEntry entryAmount;

	public AttributeSelectionPanel()
	{
		super(CGConstants.PANELID_OPTIONS, "GUI:add.attribute");
	}

	@Override
	protected void addComponents()
	{
		add(entryAmount);
		add(comboboxAttribute);
		add(comboboxOperation);
		addLine(buttonSave, buttonLoad);
	}

	@Override
	protected void createComponents()
	{
		buttonSave = new SaveButton(CGConstants.OBJECT_ATTRIBUTE, this);
		buttonLoad = new LoadButton(CGConstants.OBJECT_ATTRIBUTE, this);

		entryAmount = new CEntry(CGConstants.DATAID_NONE, "GUI:attribute.amount", "1");
		entryAmount.setTextField("1");

		comboboxAttribute = new CComboBox(CGConstants.DATAID_NONE, "GUI:attribute.select", Registry.getObjectList(CGConstants.OBJECT_ATTRIBUTE), null);
		comboboxOperation = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:attribute.operation", 3);
	}

	@Override
	protected void createListeners()
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

	@Override
	public void updateLang()
	{}

	@Override
	public void load(Object object)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(getPanelId(), object);
		setupFrom(data);
	}

	@Override
	public Object getObjectToSave()
	{
		return getAttribute();
	}

}
