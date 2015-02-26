package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.main.Constants;

public class TagSpawnPotentials extends TagList
{

	public TagSpawnPotentials()
	{
		super("block.SpawnPotentials", "mob_spawner");
	}

	@Override
	public void askValue()
	{
		panel = new ListSelectionPanel("GENERAL:entity", Constants.OBJECT_ENTITY);
		((ListSelectionPanel) panel).setList(getValue());

		if (showPanel()) return;

		setValue(((ListSelectionPanel) panel).getList());
	}
}
