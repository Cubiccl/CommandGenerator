package commandGenerator.arguments.tags.specific;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagDouble;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;

public class TagPos extends TagList
{
	private boolean haveIds;

	public TagPos(String id, boolean haveIds, String... applicable)
	{
		super(id, applicable);
		this.haveIds = haveIds;
		List<Tag> value = new ArrayList<Tag>();
		if (this.haveIds) value.add(new TagDouble("X").setValue(0.0D));
		else value.add(new TagDouble().setValue(0.0D));
		if (this.haveIds) value.add(new TagDouble("Y").setValue(0.0D));
		else value.add(new TagDouble().setValue(0.0D));
		if (this.haveIds) value.add(new TagDouble("Z").setValue(0.0D));
		else value.add(new TagDouble().setValue(0.0D));
		setValue(value);
	}

	@Override
	public void askValue()
	{
		panel = new CoordSelectionPanel("TAGS:" + getId(), false, false);

		double x = 0, y = 0, z = 0;
		if (this.haveIds)
		{
			for (Tag tag : this.getValue())
			{
				if (tag.getId().equals("X")) x = ((TagDouble) tag).getValue();
				if (tag.getId().equals("Y")) x = ((TagDouble) tag).getValue();
				if (tag.getId().equals("Z")) x = ((TagDouble) tag).getValue();
			}
		}
		x = ((TagDouble) getValue().get(0)).getValue();
		y = ((TagDouble) getValue().get(1)).getValue();
		z = ((TagDouble) getValue().get(2)).getValue();
		((CoordSelectionPanel) panel).setupFrom(new Coordinates(x, y, z, true));

		if (showPanel()) return;
		if (((CoordSelectionPanel) panel).generateCoord() == null) return;

		Coordinates coords = ((CoordSelectionPanel) panel).generateCoord();
		if (!haveIds) setValue(coords.toTagPos());
		else
		{
			List<Tag> value = new ArrayList<Tag>();
			value.add(new TagDouble("X").setValue(coords.getCoord(Coordinates.X)));
			value.add(new TagDouble("Y").setValue(coords.getCoord(Coordinates.Y)));
			value.add(new TagDouble("Z").setValue(coords.getCoord(Coordinates.Z)));
			this.setValue(value);
		}
	}
}
