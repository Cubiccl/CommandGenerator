package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.main.Constants;

public class TagEnchants extends TagList
{

	public TagEnchants(String id, String... applicable)
	{
		super(id, applicable);
	}

	@Override
	public void askValue()
	{
		panel = new ListSelectionPanel("TAGS:" + getId(), Constants.OBJECT_ENCHANT);
		((ListSelectionPanel) panel).setList(getValue());
		
		if (showPanel()) return;
		
		setValue(((ListSelectionPanel) panel).getList());
	}

}
