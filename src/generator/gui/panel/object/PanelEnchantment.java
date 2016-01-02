package generator.gui.panel.object;

import java.awt.AWTEvent;

import generator.CommandGenerator;
import generator.gui.CLabel;
import generator.gui.combobox.CSearchBox;
import generator.gui.panel.CPanel;
import generator.gui.spinner.CSpinner;
import generator.gui.textfield.CTextfield;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.main.GenerationException;
import generator.registry.Enchantment;
import generator.registry.instance.AppliedEnchantment;

/** Used to input an Enchantment. */
@SuppressWarnings("serial")
public class PanelEnchantment extends CPanel implements IClickEvent
{
	public static final int SELECT = 0;

	/** The combobox to select an enchantment. */
	private CSearchBox comboboxEnchantment;
	/** The list of all enchantments. */
	private Enchantment[] enchantments;
	/** True if the Enchantment level should be restricted to the survival friendly levels. */
	private boolean isRestricted;
	/** Labels. */
	private CLabel labelLevel, labelEnchantment;
	/** Spinner to select the level if restricted. */
	private CSpinner spinnerLevel;
	/** Textfield to input the level if not restricted. */
	private CTextfield textfieldLevel;

	/** Creates a new Panel Enchantment.
	 * 
	 * @param isRestricted - True if the Enchantment level should be restricted to the survival friendly levels. */
	public PanelEnchantment(boolean isRestricted)
	{
		super();
		this.isRestricted = isRestricted;
		this.enchantments = CommandGenerator.getRegistry().getEnchantments();

		this.labelEnchantment = new CLabel("GUI:enchantment.enchant");
		this.labelLevel = new CLabel("GUI:enchantment.level");

		this.comboboxEnchantment = new CSearchBox();
		this.comboboxEnchantment.addActionListener(new ClickEvent(this, SELECT));
		if (this.isRestricted) this.spinnerLevel = new CSpinner(0, 0);
		else this.textfieldLevel = new CTextfield();

		this.add(this.labelEnchantment, this.gbc);
		this.gbc.gridy++;
		this.add(this.labelLevel, this.gbc);
		this.gbc.gridx++;
		this.gbc.gridy = 0;
		this.add(this.comboboxEnchantment, this.gbc);
		this.gbc.gridy++;
		if (this.isRestricted) this.add(this.spinnerLevel, this.gbc);
		else this.add(this.textfieldLevel, this.gbc);

		this.updateLang();
	}

	/** @return The Enchantment inpu by the user. */
	public AppliedEnchantment generateEnchantment() throws GenerationException
	{
		if (this.isRestricted) { return new AppliedEnchantment(this.getSelectedEnchantment(), (int) this.spinnerLevel.getValue()); }

		int level = 0;
		try
		{
			level = Integer.parseInt(this.textfieldLevel.getText());
		} catch (NumberFormatException e)
		{
			throw new GenerationException(CommandGenerator.translate("GUI:error.integer").replaceAll("<value>", this.textfieldLevel.getText()));
		}

		return new AppliedEnchantment(this.getSelectedEnchantment(), level);
	}

	/** @return The currently selected Enchantment type. */
	private Enchantment getSelectedEnchantment()
	{
		return this.enchantments[this.comboboxEnchantment.getSelectedIndex()];
	}

	@Override
	public void onEvent(int eventID, AWTEvent event)
	{
		switch (eventID)
		{
			case SELECT:
				this.onSelection();
				break;

			default:
				break;
		}
	}

	/** Called when an enchantment is selected. */
	private void onSelection()
	{
		if (this.isRestricted && this.spinnerLevel != null)
		{
			int selection = (int) this.spinnerLevel.getValue();
			this.spinnerLevel.setBounds(1, this.getSelectedEnchantment().getMaxLevel());
			if (selection >= 1 && selection <= this.getSelectedEnchantment().getMaxLevel()) this.spinnerLevel.setValue(selection);
		}
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		String[] names = new String[this.enchantments.length];
		for (int i = 0; i < names.length; i++)
		{
			names[i] = this.enchantments[i].getName();
		}

		int index = this.comboboxEnchantment.getSelectedIndex();
		if (index == -1) index++;
		this.comboboxEnchantment.setValues(names);
		this.comboboxEnchantment.setSelectedIndex(index);
	}

}
