package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.main.Constants;

public class TagCanDestroy extends TagList
{

	public TagCanDestroy()
	{
		super("item.CanDestroy", "LIST=items");
	}

	@Override
	public void askValue()
	{
		Object[] ids = new String[Registerer.getList(Constants.LIST_BLOCKS).length];
		for (int i = 0; i < ids.length; i++)
			ids[i] = Registerer.getList(Constants.LIST_BLOCKS)[i].getId();

		panel = new ListSelectionPanel("TAGS:" + getId(), Constants.OBJECT_STRING, ids);
		((ListSelectionPanel) panel).setList(getValue());
		if (showPanel()) return;
		setValue(((ListSelectionPanel) panel).getList());
	}

}
