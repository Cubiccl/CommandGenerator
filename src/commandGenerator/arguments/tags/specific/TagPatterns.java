package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;

public class TagPatterns extends TagList
{

	public TagPatterns()
	{
		super("block.Patterns", "standing_banner:wall_banner:banner");
	}

	@Override
	public void askValue()
	{
		panel = new ListSelectionPanel("TAGS:Patterns", ObjectBase.TAG_PATTERN);
		((ListSelectionPanel) panel).setList(getValue());

		if (showPanel()) return;

		setValue(((ListSelectionPanel) panel).getList());
	}

}
