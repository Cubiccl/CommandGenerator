package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.components.combobox.ChoiceComboBox;
import commandGenerator.main.DisplayHelper;

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

	@Override
	public boolean matches(List<String> data)
	{
		for (String choice : this.choices) if (data.get(0).equals(choice)) return true;
		DisplayHelper.log(data.get(0) + " is not a valid argument.");
		return false;
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.box.setSelected(data.get(0));
	}

}
