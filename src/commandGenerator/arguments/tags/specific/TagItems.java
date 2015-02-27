package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.main.CGConstants;

public class TagItems extends TagList
{

	public TagItems(String id, String... applicable)
	{
		super(id, applicable);
	}

	@Override
	public void askValue()
	{
		Object[] list = Registry.getList(CGConstants.LIST_ITEMS);
		panel = new ListSelectionPanel("TAGS:" + getId(), CGConstants.OBJECT_ITEM, list);
		if (showPanel()) return;
		setValue(((ListSelectionPanel) panel).getList());
	}

}
