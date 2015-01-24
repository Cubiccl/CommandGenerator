package commandGenerator.arguments.tags.specific;

import java.util.ArrayList;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.PagesSelectionPanel;

public class TagPages extends TagList
{

	public TagPages()
	{
		super("item.pages", "written_book");
		setValue(new ArrayList<Tag>());
	}

	@Override
	public void askValue()
	{
		panel = new PagesSelectionPanel();
		((PagesSelectionPanel) panel).setup(getValue());

		if (showPanel()) return;

		setValue(((PagesSelectionPanel) panel).getPages());
	}

}
