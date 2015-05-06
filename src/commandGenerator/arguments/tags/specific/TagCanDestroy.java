package commandGenerator.arguments.tags.specific;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.main.CGConstants;

public class TagCanDestroy extends TagList
{

	public TagCanDestroy()
	{
		super("item.CanDestroy", "LIST=items");
	}

	@Override
	public void askValue()
	{
		Object[] ids = new String[Generator.registry.getList(CGConstants.LIST_BLOCKS).length];
		for (int i = 0; i < ids.length; i++)
			ids[i] = Generator.registry.getList(CGConstants.LIST_BLOCKS)[i].getId();

		panel = new ListSelectionPanel("TAGS:" + getId(), ObjectBase.STRING, ids);
		((ListSelectionPanel) panel).setList(getValue());
		if (showPanel()) return;
		setValue(((ListSelectionPanel) panel).getList());
	}

}
