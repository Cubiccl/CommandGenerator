package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.DropChancesSelectionPanel;

public class TagDropChances extends TagList
{

	public TagDropChances()
	{
		super("entity.DropChances", "LIST=mob");
	}

	@Override
	public void askValue()
	{
		panel = new DropChancesSelectionPanel("TAGS:" + getId());
		((DropChancesSelectionPanel) panel).setup(getValue());
		showPanel();
		setValue(((DropChancesSelectionPanel) panel).getDropChances());
	}

}
