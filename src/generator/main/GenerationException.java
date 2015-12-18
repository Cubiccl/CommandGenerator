package generator.main;

import generator.CommandGenerator;

/** An error occurring when the user would input wrong data in the structure arguments. */
@SuppressWarnings("serial")
public class GenerationException extends Exception
{
	private String message;

	public GenerationException(String message)
	{
		this.message = message;
	}

	@Override
	public String getMessage()
	{
		return this.message;
	}

	/** Displays the error message to the user. */
	public void showMessage()
	{
		Utils.showMessage(CommandGenerator.translate("GUI:error.generation"), this.getMessage());
	}

}
