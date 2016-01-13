package generator.registry.command;

import generator.main.GenerationException;
import generator.main.Text;
import generator.main.Utils;

import java.awt.Component;

/** This Argument will always be in the Command and doesn't need user input. */
public class StaticArgument extends Argument
{
	private final String value;

	/** Creates a new Static Argument.
	 * 
	 * @param value - Its value. */
	public StaticArgument(String value)
	{
		super(true, Utils.countOccurrences(value, " "));
		this.value = value;
	}

	@Override
	public void createGui()
	{}

	@Override
	protected String generateValue() throws GenerationException
	{
		return this.value;
	}

	@Override
	public Component getComponent()
	{
		return null;
	}

	@Override
	public void updateLang()
	{}

	@Override
	protected void verifyValue(String value) throws GenerationException
	{
		if (!value.equals(this.value)) throw new GenerationException(new Text("GUI", "error.value", false).addReplacement("<value>", value).addReplacement(
				"<correct>", this.value));
	}

}
