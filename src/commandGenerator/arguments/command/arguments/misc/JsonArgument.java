package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.main.CGConstants;

public class JsonArgument extends Argument
{

	private ListSelectionPanel panel;

	public JsonArgument(String id, boolean isCompulsery)
	{
		super(id, Argument.JSON, isCompulsery, 1);
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public void initGui()
	{
		this.panel = new ListSelectionPanel("GUI:" + this.getId(), CGConstants.OBJECT_JSON);
	}

	@Override
	public String generateCommand()
	{
		return new TagList() {
			public void askValue()
			{}
		}.setValue(this.panel.getList()).commandStructure();
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

}
