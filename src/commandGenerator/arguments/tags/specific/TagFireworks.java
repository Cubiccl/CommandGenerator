package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.FireworksSelectionPanel;

public class TagFireworks extends TagCompound
{

	public TagFireworks()
	{
		super("item.Fireworks", "fireworks");
		addTag(new TagInt("Flight").setValue(0));
		addTag(new TagList("Explosions") {
			@Override
			public void askValue()
			{}
		});
	}

	@Override
	public void askValue()
	{

		panel = new FireworksSelectionPanel("TAGS:" + getId());
		((FireworksSelectionPanel) panel).setup(((TagInt) get(0)).getValue(), ((TagList) get(1)).getValue());

		if (showPanel()) return;

		clear();
		addTag(new TagInt("Flight").setValue(((FireworksSelectionPanel) panel).getFlight()));
		addTag(((FireworksSelectionPanel) panel).getExplosions());

	}

}
