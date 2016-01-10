package generator.registry.command;

import generator.gui.checkbox.CCheckbox;
import generator.main.GenerationException;
import generator.main.Text;

import java.awt.Component;

/** An argument that can has only 2 options. Represented by a Checkbox. */
public class BooleanArgument extends Argument
{
	private CCheckbox checkbox;
	/** The text ID to use for the Checkbox. */
	private Text text;
	/** The values to use to generate. "true" and "false" by default. */
	private String trueValue, falseValue;

	/** Creates a new Boolean Argument.
	 * 
	 * @param isCompulsory - True if compulsory.
	 * @param text - The text ID for the Checkbox. */
	public BooleanArgument(boolean isCompulsory, Text text)
	{
		super(isCompulsory, 1);
		this.text = text;
		this.trueValue = "true";
		this.falseValue = "false";
	}

	@Override
	public void createGui()
	{
		this.checkbox = new CCheckbox(this.text);
	}

	@Override
	public String generate() throws GenerationException
	{
		return this.checkbox.isSelected() ? this.trueValue : this.falseValue;
	}

	@Override
	public Component getComponent()
	{
		return this.checkbox;
	}

	@Override
	public boolean isUsed()
	{
		if (super.isUsed()) return super.isUsed();
		return this.checkbox.isSelected();
	}

	/** Changes the text to display for "false".
	 * 
	 * @param falseValue - The new value. */
	public void setFalseValue(String falseValue)
	{
		this.falseValue = falseValue;
	}

	/** Changes the text to display for "true".
	 * 
	 * @param trueValue - The new value. */
	public void setTrueValue(String trueValue)
	{
		this.trueValue = trueValue;
	}

	@Override
	public void updateLang()
	{
		if (this.checkbox != null) this.checkbox.updateLang();
	}

}
