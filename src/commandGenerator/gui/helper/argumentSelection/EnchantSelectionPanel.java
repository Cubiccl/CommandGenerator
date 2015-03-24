package commandGenerator.gui.helper.argumentSelection;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import commandGenerator.arguments.objects.EnchantType;
import commandGenerator.arguments.objects.Enchantment;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.button.LoadButton;
import commandGenerator.gui.helper.components.button.SaveButton;
import commandGenerator.gui.helper.components.combobox.CComboBox;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.gui.helper.components.spinner.NumberSpinner;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class EnchantSelectionPanel extends HelperPanel implements IBox, ISave
{

	private CButton buttonSave, buttonLoad;
	private CComboBox comboboxEnchant;
	private CEntry entryLevel;
	private boolean limited;
	private NumberSpinner spinnerLevel;

	public EnchantSelectionPanel(String id, String title, boolean limited)
	{
		super(id, title, limited);
	}

	@Override
	protected void addComponents()
	{
		add(comboboxEnchant);
		add(spinnerLevel);
		add(entryLevel);
		addLine(buttonSave, buttonLoad);
	}

	@Override
	protected void createComponents()
	{
		buttonSave = new SaveButton(CGConstants.OBJECT_ENCHANT, this);
		buttonLoad = new LoadButton(CGConstants.OBJECT_ENCHANT, this);

		entryLevel = new CEntry(CGConstants.DATAID_NONE, "GUI:enchant.level", "1");
		entryLevel.setVisible(!limited);

		spinnerLevel = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:enchant.level", 1, 5, null);
		spinnerLevel.setVisible(limited);

		comboboxEnchant = new CComboBox(CGConstants.DATAID_NONE, "GUI:enchant.choose", Registry.getObjectList(CGConstants.OBJECT_ENCHANT), this);
	}

	@Override
	protected void createListeners()
	{}

	public Enchantment generateEnchantment()
	{
		DisplayHelper.log("Generating enchantment");
		int level;
		if (limited) level = spinnerLevel.getValue();
		else
		{
			try
			{
				level = Integer.parseInt(entryLevel.getText());
				if (level < 1)
				{
					JOptionPane.showMessageDialog(null, Lang.get("WARNING:min").replaceAll("<min>", "1"), Lang.get("WARNING:title"),
							JOptionPane.WARNING_MESSAGE);
					return null;
				}
			} catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, Lang.get("WARNING:min").replaceAll("<min>", "1"), Lang.get("WARNING:title"), JOptionPane.WARNING_MESSAGE);
				return null;
			}
		}
		Enchantment enchant = new Enchantment((EnchantType) comboboxEnchant.getValue(), level, limited);
		if (limited & !enchant.isCorrect()) return null;
		return enchant;
	}

	@Override
	protected void setupDetails(Object[] details)
	{
		limited = (boolean) details[0];
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		Enchantment enchant = (Enchantment) data.get(getPanelId());
		comboboxEnchant.setSelected(enchant.getType());
		spinnerLevel.setSelected(enchant.getLevel());
		entryLevel.setTextField(Integer.toString(enchant.getLevel()));
	}

	@Override
	public void updateCombobox()
	{
		spinnerLevel.setValues(1, ((EnchantType) comboboxEnchant.getValue()).getMax());
	}

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
		return generateEnchantment();
	}

	public void setEnchantment(EnchantType enchant)
	{
		this.comboboxEnchant.setSelected(enchant);
	}

	public void setLevel(int level)
	{
		this.entryLevel.setTextField(String.valueOf(level));
		this.spinnerLevel.setSelected(level);
	}

}
