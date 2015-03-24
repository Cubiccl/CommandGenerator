package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.dataTag.DisplaySelectionPanel;

public class TagDisplay extends TagCompound
{

	private boolean hasName = false;

	public TagDisplay()
	{
		super("item.display", "LIST=items");
		addTag(new TagList("Lore") {
			@Override
			public void askValue()
			{}
		});
	}

	@Override
	public void askValue()
	{

		panel = new DisplaySelectionPanel();
		if (hasName) ((DisplaySelectionPanel) panel).setup(((TagString) get(0)).getValue(), ((TagList) get(1)).getValue(), 0);
		else ((DisplaySelectionPanel) panel).setup(null, ((TagList) get(0)).getValue(), 0);
		if (showPanel()) return;
		String name = ((DisplaySelectionPanel) panel).getCustomName();
		TagList lore = ((DisplaySelectionPanel) panel).getLores();
		String color = ((DisplaySelectionPanel) panel).getColor();

		clear();
		if (name != null) addTag(new TagString("Name").setValue(name));
		hasName = name != null;
		addTag(lore);
		if (color != null) addTag(new TagInt("color").setValue(Integer.parseInt(color)));

	}

}
