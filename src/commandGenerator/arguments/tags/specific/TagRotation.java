package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.RotationSelectionPanel;
import commandGenerator.main.CGConstants;

public class TagRotation extends TagList
{

	public TagRotation()
	{
		super("entity.Rotation", "LIST=allEntities");
	}

	@Override
	public void askValue()
	{
		panel = new RotationSelectionPanel("TAGS:" + getId(), CGConstants.DATAID_NONE);
		showPanel();
		setValue(((RotationSelectionPanel) panel).getRotations());
	}

}
