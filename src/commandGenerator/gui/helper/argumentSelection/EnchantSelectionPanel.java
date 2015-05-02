package commandGenerator.gui.helper.argumentSelection;

import javax.swing.JOptionPane;

import commandGenerator.arguments.objects.EnchantType;
import commandGenerator.arguments.objects.Enchantment;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.button.LoadButton;
import commandGenerator.gui.helper.components.button.SaveButton;
import commandGenerator.gui.helper.components.combobox.ObjectComboBox;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.gui.helper.components.spinner.NumberSpinner;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class EnchantSelectionPanel extends HelperPanel implements IBox, ISave
{

	private CButton buttonSave, buttonLoad;
	private ObjectComboBox comboboxEnchant;
	private CEntry entryLevel;
	private boolean limited;
	private NumberSpinner spinnerLevel;

	public EnchantSelectionPanel(String title, boolean limited)
	{
		super(title);
		this.limited = limited;
		
		this.initGui();
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
		buttonSave = new SaveButton(ObjectBase.ENCHANTMENT, this);
		buttonLoad = new LoadButton(ObjectBase.ENCHANTMENT, this);

		entryLevel = new CEntry("GUI:enchant.level", "1");
		entryLevel.setVisible(!limited);

		spinnerLevel = new NumberSpinner("GUI:enchant.level", 1, 5, null);
		spinnerLevel.setVisible(limited);

		comboboxEnchant = new ObjectComboBox("GUI:enchant.choose", Registry.getObjectList(ObjectBase.ENCHANTMENT), this);
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
	public Object getObjectToSave()
	{
		return generateEnchantment();
	}

	@Override
	public void load(Object object)
	{
		setupFrom((Enchantment) object);
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

	public void setupFrom(Enchantment enchant)
	{
		comboboxEnchant.setSelected(enchant.getEnchantType());
		spinnerLevel.setSelected(enchant.getLevel());
		entryLevel.setTextField(Integer.toString(enchant.getLevel()));
	}

	@Override
	public void updateCombobox()
	{
		spinnerLevel.setValues(1, ((EnchantType) comboboxEnchant.getValue()).getMaxLevel());
	}

}
