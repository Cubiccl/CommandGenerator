package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.main.CGConstants;

public class TagCanPlace extends TagList
{

	public TagCanPlace()
	{
		super("item.CanPlaceOn", "LIST=place");
	}

	@Override
	public void askValue()
	{
		Object[] ids = new String[Registry.getList(CGConstants.LIST_BLOCKS).length];
		for (int i = 0; i < ids.length; i++)
			ids[i] = Registry.getList(CGConstants.LIST_BLOCKS)[i].getId();

		panel = new ListSelectionPanel("TAGS:" + getId(), CGConstants.OBJECT_STRING, ids);
		((ListSelectionPanel) panel).setList(getValue());
		if (showPanel()) return;
		setValue(((ListSelectionPanel) panel).getList());
	}

}
