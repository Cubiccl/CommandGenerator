package commandGenerator.arguments.tags.specific;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagDouble;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.main.CGConstants;

public class TagPos extends TagList
{

	public TagPos(String id, String... applicable)
	{
		super(id, applicable);
		List<Tag> value = new ArrayList<Tag>();
		value.add(new TagDouble().setValue(0.0D));
		value.add(new TagDouble().setValue(0.0D));
		value.add(new TagDouble().setValue(0.0D));
		setValue(value);
	}

	@Override
	public void askValue()
	{
		panel = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "TAGS:" + getId(), false, false);

		Coordinates coords = new Coordinates(((TagDouble) getValue().get(0)).getValue(), ((TagDouble) getValue().get(1)).getValue(), ((TagDouble) getValue()
				.get(2)).getValue());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(CGConstants.PANELID_COORDS, coords);
		((CoordSelectionPanel) panel).setupFrom(data);

		if (showPanel()) return;
		if (((CoordSelectionPanel) panel).generateCoord() == null) return;
		
		setValue(((CoordSelectionPanel) panel).generateCoord().toTagPos());
	}

}
