package commandGenerator.arguments.tags.specific;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.ObjectBase;
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
		Object[] list = Generator.registry.getList(CGConstants.LIST_ITEMS);
		panel = new ListSelectionPanel("TAGS:" + getId(), ObjectBase.ITEM, list);
		if (showPanel()) return;
		setValue(((ListSelectionPanel) panel).getList());
	}

}
