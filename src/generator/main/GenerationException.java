package generator.main;

/** An error occurring when the user would input wrong data in the structure arguments. */
@SuppressWarnings("serial")
public class GenerationException extends Exception
{
	public static Text general = new Text("GUI", "error.generation");

	private Text message;

	public GenerationException(Text text)
	{
		this.message = text;
	}

	@Override
	public String getMessage()
	{
		return this.message.getValue();
	}

	/** Displays the error message to the user. */
	public void showMessage()
	{
		Utils.showMessage(general, this.message);
	}

}
