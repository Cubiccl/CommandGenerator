package commandGenerator.arguments.tags.specific;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagFloat;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.RotationSelectionPanel;
import commandGenerator.main.CGConstants;

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
		panel = new RotationSelectionPanel(CGConstants.PANELID_COORDS, "TAGS:" + getId());

		Map<String, Object> data = new HashMap<String, Object>();
		data.put(CGConstants.PANELID_COORDS, new Coordinates(0D, 0D, 0D, ((TagFloat) getValue().get(0)).getValue(), ((TagFloat) getValue().get(1)).getValue(),
				new boolean[] { false, false, false }, false));
		((RotationSelectionPanel) panel).setupFrom(data);

		if (showPanel()) return;

		setValue(((RotationSelectionPanel) panel).getRotations());
	}

}
