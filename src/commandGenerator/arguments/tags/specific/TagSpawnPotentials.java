package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.SpawnPotentialsSelectionPanel;

public class TagSpawnPotentials extends TagList {

	public TagSpawnPotentials() {
		super("block.SpawnPotentials", "mob_spawner");
	}

	@Override
	public void askValue() {
		panel = new SpawnPotentialsSelectionPanel();
		showPanel();
		setValue(((SpawnPotentialsSelectionPanel) panel).getSpawnPotentials());
	}

}
