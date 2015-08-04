package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.ObjectCreator;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagDouble;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;

public class TagExitPortal extends TagCompound
{

	public TagExitPortal(String applicable)
	{
		super("ExitPortal", applicable);
		this.addTag(new TagDouble("X").setValue(0D));
		this.addTag(new TagDouble("Y").setValue(0D));
		this.addTag(new TagDouble("Z").setValue(0D));
	}

	@Override
	public void askValue()
	{
		double x = 0, y = 0, z = 0;
		for (int i = 0; i < this.getValue().size(); i++)
		{
			Tag tag = this.getValue().get(i);
			if (tag.getId().equals("X")) x = ((TagDouble) tag).getValue();
			if (tag.getId().equals("Y")) y = ((TagDouble) tag).getValue();
			if (tag.getId().equals("Z")) z = ((TagDouble) tag).getValue();
		}
		this.panel = new CoordSelectionPanel("GUI:coords", false, false);
		((CoordSelectionPanel) this.panel).setupFrom(ObjectCreator.generateCoordinates(x, y, z));
		if (this.showPanel()) return;

		this.clear();
		Coordinates coord = ((CoordSelectionPanel) panel).generateCoord();
		this.addTag(new TagDouble("X").setValue(coord.getCoord(Coordinates.X)));
		this.addTag(new TagDouble("Y").setValue(coord.getCoord(Coordinates.Y)));
		this.addTag(new TagDouble("Z").setValue(coord.getCoord(Coordinates.Z)));
	}

}
