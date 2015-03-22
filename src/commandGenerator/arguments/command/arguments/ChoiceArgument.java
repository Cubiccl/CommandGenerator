package commandGenerator.arguments.command.arguments;

import java.awt.Component;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.components.combobox.ChoiceComboBox;

public class ChoiceArgument extends Argument
{
	private String[] choices;
	private ChoiceComboBox box;
	private boolean hasHelp, translate;

	public ChoiceArgument(String id, boolean isCompulsery, String... choices)
	{
		super(id, Argument.CHOICE, isCompulsery, 1);
		this.choices = choices;
		this.hasHelp = false;
		this.translate = true;
	}

	@Override
	public Component generateComponent()
	{
		return this.box;
	}

	@Override
	public String generateCommand()
	{
		return this.choices[this.box.getSelectedIndex()];
	}

	@Override
	public void initGui()
	{
		this.box = new ChoiceComboBox(this.getId(), this.choices, this.hasHelp, this.translate);
	}

	public ChoiceArgument addHelpButton()
	{
		this.hasHelp = true;
		return this;
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

	public ChoiceArgument setNoTranslation()
	{
		this.translate = false;
		return this;
	}

}
