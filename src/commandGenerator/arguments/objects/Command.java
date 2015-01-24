package commandGenerator.arguments.objects;

import java.util.Map;

import commandGenerator.gui.OptionsPanel;
import commandGenerator.main.Lang;

public abstract class Command
{

	private String id;

	/** Creates a new Command
	 * 
	 * @param id
	 *            - The Command ID */
	public Command(String id)
	{
		this.id = id;
	}

	/** Returns the Panel to use to display the Command's options. */
	public abstract OptionsPanel getOptionsPanel();

	/** Returns the Command's ID */
	public String getId()
	{
		return id;
	}

	/** Returns the Command's description */
	public String getDescription()
	{
		return Lang.get("HELP:command." + id);
	}

	/** Generates the data used for the GUI from an already generated command */
	public abstract Map<String, Object> generateSetup(String string);

}
