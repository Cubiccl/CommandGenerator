package commandGenerator.gui.helper.argumentSelection.dataTag;

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
		super("GUI:add.attribute");
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

		entryAmount = new CEntry("GUI:attribute.amount", "1");
		entryAmount.setTextField("1");

		comboboxAttribute = new CComboBox("GUI:attribute.select", Registry.getObjectList(CGConstants.OBJECT_ATTRIBUTE), null);
		comboboxOperation = new LangComboBox("RESOURCES:attribute.operation", 3);
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

	@Override
	public Object getObjectToSave()
	{
		return getAttribute();
	}

	@Override
	public void load(Object object)
	{
		setupFrom((Attribute) object);
	}

	public void setupFrom(Attribute attribute)
	{
		comboboxOperation.setSelectedIndex(attribute.getOperation());
		entryAmount.setTextField(Double.toString(attribute.getAmount()));
		comboboxAttribute.setSelected(attribute.getType());
	}

	@Override
	public void updateLang()
	{}

}
