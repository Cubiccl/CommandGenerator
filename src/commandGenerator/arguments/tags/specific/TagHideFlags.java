package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagInt;
import commandGenerator.gui.helper.argumentSelection.dataTag.FlagSelectionPanel;

public class TagHideFlags extends TagInt
{

	public TagHideFlags()
	{
		super("item.HideFlags", "LIST=items");
	}

	@Override
	public void askValue()
	{

		panel = new FlagSelectionPanel();
		if (showPanel()) return;
		value = ((FlagSelectionPanel) panel).getHideFlags();

	}

}
