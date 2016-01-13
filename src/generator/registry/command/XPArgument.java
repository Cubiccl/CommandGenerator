package generator.registry.command;

import generator.gui.checkbox.CCheckbox;
import generator.main.GenerationException;
import generator.main.Text;

/** Used to give experience. */
public class XPArgument extends NumberArgument
{
	/** Checkbox to indicate if the amount is in levels. */
	private CCheckbox checkboxLevel;

	public XPArgument()
	{
		super(true, new Text("GUI", "xp.amount"));
		this.setInteger();
	}

	@Override
	public void createGui()
	{
		super.createGui();

		this.checkboxLevel = new CCheckbox(new Text("GUI", "xp.level"));
		this.panel.add(this.checkboxLevel);
	}

	@Override
	public String generateValue() throws GenerationException
	{
		return this.checkboxLevel.isSelected() ? super.generateValue() + "L" : super.generateValue();
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		if (this.checkboxLevel != null) this.checkboxLevel.updateLang();
	}

	@Override
	protected void verifyValue(String value) throws GenerationException
	{
		if (value.endsWith("L")) value = value.substring(0, value.length() - 1);
		super.verifyValue(value);
	}

}
