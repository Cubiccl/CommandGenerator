package generator.main;

import generator.CommandGenerator;
import generator.gui.panel.CPanel;

/** A State of the Generator. */
public class State
{
	/** The Component to display if active. */
	private CPanel component;
	/** The ID of this State's name. */
	private Text text;

	/** @param text - The ID of this state's name.
	 * @param component - The component to display. */
	public State(Text text, CPanel component)
	{
		this.text = text;
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
		return CommandGenerator.translate(this.text);
	}

	/** @param text - The new name. */
	public void setName(Text text)
	{
		this.text = text;
	}

}
