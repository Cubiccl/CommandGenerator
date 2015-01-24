package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagBoolean;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.ExplosionSelectionPanel;

public class TagExplosion extends TagCompound
{
	private boolean colors, fades;

	public TagExplosion()
	{
		super("item.Explosion", "firework_charge");
		addTag(new TagBoolean("Flicker").setValue(false));
		addTag(new TagBoolean("Trail").setValue(false));
		addTag(new TagInt("Type").setValue(0));
		colors = false;
		fades = false;
	}

	@Override
	public void askValue()
	{

		panel = new ExplosionSelectionPanel();
		TagList color = new TagList("Colors") {
			public void askValue()
			{}
		}, fade = new TagList("FadeColors") {
			public void askValue()
			{}
		};
		if (colors) color = (TagList) get(3);
		if (colors && fades) fade = (TagList) get(4);
		if (!colors && fades) fade = (TagList) get(3);
		((ExplosionSelectionPanel) panel).setup(((TagBoolean) get(0)).getValue(), ((TagBoolean) get(1)).getValue(), ((TagInt) get(2)).getValue(), color, fade);
		if (showPanel()) return;

		clear();
		addTag(new TagBoolean("Flicker").setValue(((ExplosionSelectionPanel) panel).isFlicker()));
		addTag(new TagBoolean("Trail").setValue(((ExplosionSelectionPanel) panel).isTrail()));
		addTag(new TagInt("Type").setValue(((ExplosionSelectionPanel) panel).getType()));

		colors = ((ExplosionSelectionPanel) panel).getColors() != null;
		fades = ((ExplosionSelectionPanel) panel).getFadeColors() != null;
		
		if (((ExplosionSelectionPanel) panel).getColors() != null) addTag(((ExplosionSelectionPanel) panel).getColors());
		if (((ExplosionSelectionPanel) panel).getFadeColors() != null) addTag(((ExplosionSelectionPanel) panel).getFadeColors());

	}
}
