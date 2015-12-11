package generator.main;

import generator.CommandGenerator;
import generator.gui.panel.CPanel;

public class State
{
	private CPanel component;
	private String textID;

	public State(String textID, CPanel component)
	{
		this.textID = textID;
		this.component = component;
	}

	public CPanel getComponent()
	{
		return this.component;
	}

	public String getName()
	{
		return CommandGenerator.translate(this.textID);
	}

	public void setName(String textID)
	{
		this.textID = textID;
	}

}
