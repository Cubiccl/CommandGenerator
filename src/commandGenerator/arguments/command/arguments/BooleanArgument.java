package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.components.CCheckBox;

public class BooleanArgument extends Argument
{

	private CCheckBox box;
	private String falseValue, trueValue;

	public BooleanArgument(String id, boolean isCompulsery)
	{
		super(id, Argument.BOOLEAN, isCompulsery, 1);
		this.falseValue = "false";
		this.trueValue = "true";
	}

	@Override
	public Component generateComponent()
	{
		return this.box;
	}

	@Override
	public void initGui()
	{
		this.box = new CCheckBox(this.getId(), "GUI:" + this.getId());
	}

	@Override
	public String generateCommand()
	{
		if (box.isSelected()) return trueValue;
		return falseValue;
	}

	@Override
	public boolean isUsed()
	{
		return isCompulsery() || box.isSelected();
	}

	public BooleanArgument setValues(String falseValue, String trueValue)
	{
		this.falseValue = falseValue;
		this.trueValue = trueValue;
		return this;
	}

	@Override
	public boolean matches(List<String> data)
	{
		return data.get(0).equals(this.falseValue) || data.get(0).equals(this.trueValue);
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.box.setSelected(data.get(0).equals(trueValue));
	}

}
