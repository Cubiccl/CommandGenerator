package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.main.CGConstants;

public class TagPatterns extends TagList
{

	public TagPatterns()
	{
		super("block.Patterns", "standing_banner", "wall_banner", "banner");
	}

	@Override
	public void askValue()
	{
		panel = new ListSelectionPanel("GENERAL:pattern", CGConstants.OBJECT_TAG_PATTERN);
		((ListSelectionPanel) panel).setList(getValue());

		if (showPanel()) return;

		setValue(((ListSelectionPanel) panel).getList());
	}

}
