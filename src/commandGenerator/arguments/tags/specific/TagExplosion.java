package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagBoolean;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.gui.helper.argumentSelection.dataTag.ExplosionSelectionPanel;

public class TagExplosion extends TagCompound
{

	public TagExplosion()
	{
		super("item.Explosion", "firework_charge");
	}

	@Override
	public void askValue()
	{

		panel = new ExplosionSelectionPanel();
		clear();
		showPanel();

		addTag(new TagBoolean("Flicker").setValue(((ExplosionSelectionPanel) panel).isFlicker()));
		addTag(new TagBoolean("Trail").setValue(((ExplosionSelectionPanel) panel).isTrail()));
		addTag(new TagInt("Type").setValue(((ExplosionSelectionPanel) panel).getType()));

		if (((ExplosionSelectionPanel) panel).getColors() != null) addTag(((ExplosionSelectionPanel) panel).getColors());
		if (((ExplosionSelectionPanel) panel).getFadeColors() != null) addTag(((ExplosionSelectionPanel) panel).getFadeColors());

	}

}
