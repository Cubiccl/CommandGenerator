package commandGenerator.arguments.tags.specific;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagDouble;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.main.CGConstants;

public class TagAttributes extends TagList
{
	private boolean forMob;

	public TagAttributes(String id, boolean forMob, String... applicable)
	{
		super(id, applicable);
		this.forMob = forMob;
	}//TODO display

	@Override
	public void askValue()
	{
		panel = new ListSelectionPanel("TAGS:" + getId(), CGConstants.OBJECT_ATTRIBUTE);
		((ListSelectionPanel) panel).setList(getValue());
		if (showPanel()) return;
		if (!forMob) setValue(((ListSelectionPanel) panel).getList());

		TagList list = new TagList("Modifiers") {
			public void askValue()
			{}
		};
		list.setValue(((ListSelectionPanel) panel).getList());

		List<Tag> tag = new ArrayList<Tag>();
		tag.add(list);
		tag.add(new TagString("Name").setValue("Name"));
		tag.add(new TagDouble("Base").setValue(1.0d));

		setValue(tag);
	}

}
