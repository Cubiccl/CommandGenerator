package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.main.Constants;

public class TagItems extends TagList
{

	public TagItems(String id, String... applicable)
	{
		super(id, applicable);
	}

	@Override
	public void askValue()
	{
		Object[] list = Registerer.getList(Constants.LIST_ITEMS);
		panel = new ListSelectionPanel("TAGS:" + getId(), Constants.OBJECT_ITEM, list);
		if (showPanel()) return;
		setValue(((ListSelectionPanel) panel).getList());
	}

}
