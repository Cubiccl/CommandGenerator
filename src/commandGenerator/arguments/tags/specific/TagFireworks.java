package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.gui.helper.argumentSelection.dataTag.FireworksSelectionPanel;

public class TagFireworks extends TagCompound {

	public TagFireworks() {
		super("item.Fireworks", "fireworks");
	}

	@Override
	public void askValue() {

		panel = new FireworksSelectionPanel("TAGS:" + getId());
		clear();
		showPanel();

		addTag(new TagInt("Flight").setValue(((FireworksSelectionPanel) panel).getFlight()));
		addTag(((FireworksSelectionPanel) panel).getExplosions());

	}

}
