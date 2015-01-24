package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.main.CGConstants;

public class TagPos extends TagList {

	public TagPos(String id, String... applicable) {
		super(id, applicable);
	}

	@Override
	public void askValue() {
		panel = new CoordSelectionPanel("TAGS:" + getId(), CGConstants.DATAID_NONE, false, false);
		showPanel();
		setValue(((CoordSelectionPanel) panel).generateCoord().toTagPos());
	}

}
