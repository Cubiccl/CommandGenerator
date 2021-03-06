package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.components.combobox.ChoiceComboBox;
import commandGenerator.main.DisplayHelper;

public class ChoiceArgument extends Argument
{
	private ChoiceComboBox box;
	private final String[] choices;
	private boolean hasHelp, translate;

	public ChoiceArgument(String id, boolean isCompulsery, String... choices)
	{
		super(id, isCompulsery);
		this.choices = choices;
		this.hasHelp = false;
		this.translate = true;
	}

	public ChoiceArgument addHelpButton()
	{
		this.hasHelp = true;
		return this;
	}

	@Override
	public String generateCommand()
	{
		return this.choices[this.box.getSelectedIndex()];
	}

	@Override
	public Component generateComponent()
	{
		return this.box;
	}

	@Override
	public void initGui()
	{
		this.box = new ChoiceComboBox(this.getId(), this.choices, this.hasHelp, this.translate);
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

	@Override
	public boolean matches(List<String> data)
	{
		for (String choice : this.choices)
			if (data.get(0).equals(choice)) return true;
		DisplayHelper.log(data.get(0) + " is not a valid argument.");
		return false;
	}

	public ChoiceArgument setNoTranslation()
	{
		this.translate = false;
		return this;
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.box.setSelected(data.get(0));
	}

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof ChoiceArgument)) return;
		this.box.setSelected(((ChoiceArgument) toMatch).box.getSelectedValue());
	}

	@Override
	public void reset()
	{
		this.box.reset();
	}

}
