package generator.main;

import generator.CommandGenerator;
import generator.gui.panel.CPanel;

/** A State of the Generator. */
public class State
{
	/** The Component to display if active. */
	private CPanel component;
	/** The ID of this State's name. */
	private String textID;

	/** @param textID - The ID of this state's name.
	 * @param component - The component to display. */
	public State(String textID, CPanel component)
	{
		this.textID = textID;
		this.component = component;
	}

	/** @return The Component to display if active. */
	public CPanel getComponent()
	{
		return this.component;
	}

	/** @return The ID of this State's name. */
	public String getName()
	{
		return CommandGenerator.translate(this.textID);
	}

	/** @param textID - The new ID of this State's name. */
	public void setName(String textID)
	{
		this.textID = textID;
	}

}
