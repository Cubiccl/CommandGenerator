package commandGenerator.gui.helper.argumentSelection.dataTag;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.Attribute;
import commandGenerator.arguments.objects.AttributeType;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.button.LoadButton;
import commandGenerator.gui.helper.components.button.SaveButton;
import commandGenerator.gui.helper.components.combobox.ChoiceComboBox;
import commandGenerator.gui.helper.components.combobox.ObjectComboBox;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class AttributeSelectionPanel extends CPanel implements ISave
{

	private CButton buttonSave, buttonLoad;
	private ObjectComboBox comboboxAttribute;
	private ChoiceComboBox comboboxOperation;
	private CEntry entryAmount;

	public AttributeSelectionPanel()
	{
		super("GENERAL:attribute");

		this.initGui();
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
		buttonSave = new SaveButton(ObjectBase.ATTRIBUTE, this);
		buttonLoad = new LoadButton(ObjectBase.ATTRIBUTE, this);

		entryAmount = new CEntry("GUI:attribute.amount", "1");
		entryAmount.setTextField("1");

		comboboxAttribute = new ObjectComboBox("GUI:attribute.select", Generator.registry.getObjectList(ObjectBase.ATTRIBUTE), null);
		comboboxOperation = new ChoiceComboBox("attribute.operation", Attribute.operations, false);
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
		if (comboboxOperation.getSelectedIndex() == 0) return new Attribute((AttributeType) comboboxAttribute.getValue(), Double.parseDouble(amount),
				comboboxOperation.getSelectedIndex());
		return new Attribute((AttributeType) comboboxAttribute.getValue(), Double.parseDouble(amount) / 100, comboboxOperation.getSelectedIndex());
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
		comboboxOperation.setSelected(Attribute.operations[attribute.getOperation()]);
		entryAmount.setTextField(Double.toString(attribute.getAmount()));
		comboboxAttribute.setSelected(attribute.getAttributeType());
	}

}
