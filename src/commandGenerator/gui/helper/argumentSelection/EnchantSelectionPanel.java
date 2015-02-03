package commandGenerator.gui.helper.argumentSelection;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JOptionPane;

import commandGenerator.arguments.objects.EnchantType;
import commandGenerator.arguments.objects.Enchantment;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.gui.helper.components.CComboBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.IBox;
import commandGenerator.gui.helper.components.NumberSpinner;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class EnchantSelectionPanel extends HelperPanel implements IBox
{

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
	}

	@Override
	protected void createComponents()
	{
		entryLevel = new CEntry(CGConstants.DATAID_NONE, "GUI:enchant.level");
		entryLevel.setVisible(!limited);

		spinnerLevel = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:enchant.level", 1, 5, null);
		spinnerLevel.setVisible(limited);

		comboboxEnchant = new CComboBox(CGConstants.DATAID_NONE, "GUI:enchant.choose", Registerer.getObjectList(CGConstants.OBJECT_ENCHANT), this);
		comboboxEnchant.setPreferredSize(new Dimension(390, 50));
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
		Enchantment enchant = new Enchantment((EnchantType) comboboxEnchant.getValue(), level - 1, limited);
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
		spinnerLevel.setSelected(enchant.getLevel() + 1);
		entryLevel.setTextField(Integer.toString(enchant.getLevel() + 1));
	}

	@Override
	public void updateCombobox()
	{
		spinnerLevel.setValues(1, ((EnchantType) comboboxEnchant.getValue()).getMax());
	}

}
