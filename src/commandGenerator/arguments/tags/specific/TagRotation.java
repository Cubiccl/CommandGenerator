package commandGenerator.arguments.tags.specific;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagFloat;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.RotationSelectionPanel;

public class TagRotation extends TagList
{

	public TagRotation()
	{
		super("entity.Rotation", "LIST=allEntities");
		List<Tag> value = new ArrayList<Tag>();
		value.add(new TagFloat().setValue(0.0F));
		value.add(new TagFloat().setValue(0.0F));
		setValue(value);
	}

	@Override
	public void askValue()
	{
		panel = new RotationSelectionPanel("TAGS:" + getId());

		((RotationSelectionPanel) panel).setupFrom(new Coordinates(0D, 0D, 0D, ((TagFloat) getValue().get(0)).getValue(), ((TagFloat) getValue().get(1))
				.getValue(), new boolean[] { false, false, false }, false));

		if (showPanel()) return;

		setValue(((RotationSelectionPanel) panel).getRotations());
	}

}
