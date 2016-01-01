package generator.registry.command;

import generator.CommandGenerator;
import generator.gui.button.CMessageButton;
import generator.gui.combobox.CChoiceCombobox;
import generator.gui.panel.CPanelHorizontal;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.main.GenerationException;

import java.awt.AWTEvent;
import java.awt.Component;

/** An Argument which can be chosen from several values. */
public class ChoiceArgument extends Argument implements IClickEvent
{
	private static final int CHOICE = 0;

	/** The button if this has help */
	private CMessageButton buttonHelp;
	/** The combobox to choose the value */
	private CChoiceCombobox combobox;
	/** True if a button displaying details should be added */
	private boolean hasHelp;
	/** The ID for translations */
	private String id;
	/** Panel for the GUI */
	private CPanelHorizontal panel;
	/** The values */
	private String[] values;

	/** Creates a new Choice Argument.
	 * 
	 * @param isCompulsory - True if compulsory.
	 * @param hasHelp - True if a button displaying details should be added.
	 * @param id - The ID for translations.
	 * @param values - The values to choose one of. */
	public ChoiceArgument(boolean isCompulsory, boolean hasHelp, String id, String[] values)
	{
		super(isCompulsory, 1);
		this.hasHelp = hasHelp;
		this.id = id;
		this.values = values;
	}

	@Override
	public void createGui()
	{
		this.panel = new CPanelHorizontal();

		this.combobox = new CChoiceCombobox(this.id, this.values);
		this.combobox.addActionListener(new ClickEvent(this, CHOICE));
		this.panel.add(this.combobox);

		if (this.hasHelp)
		{
			this.buttonHelp = new CMessageButton();
			this.panel.add(this.buttonHelp);
			this.updateButtonLang();
		}

	}

	@Override
	public String generate() throws GenerationException
	{
		return this.combobox.getSelectedValue();
	}

	@Override
	public Component getComponent()
	{
		return this.panel;
	}

	@Override
	public void onEvent(int componentID, AWTEvent event)
	{
		switch (componentID)
		{
			case CHOICE:
				this.updateButtonLang();
				break;

			default:
				break;
		}
	}

	/** Updates the translations for the help button. Called when updating language or changing the selected value. */
	private void updateButtonLang()
	{
		if (this.buttonHelp != null)
		{
			this.buttonHelp.setTitle(CommandGenerator.translate("HELP:main").replaceAll("<object>",
					CommandGenerator.translate("CHOICE:" + this.id + "." + this.combobox.getSelectedValue())));
			this.buttonHelp.setMessage(CommandGenerator.translate("HELP:" + this.id + "." + this.combobox.getSelectedValue()));
		}
	}

	@Override
	public void updateLang()
	{
		if (this.combobox != null) this.combobox.updateLang();
		if (this.buttonHelp != null) this.updateButtonLang();
	}
}
