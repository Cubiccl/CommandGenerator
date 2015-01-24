package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.dataTag.PoseSelectionPanel;

public class TagPose extends TagCompound {

	public TagPose() {
		super("entity.Pose", "ArmorStand");
	}

	@Override
	public void askValue() {
		panel = new PoseSelectionPanel();
		showPanel();
		if (((PoseSelectionPanel) panel).getPose() != null) setValue(((PoseSelectionPanel) panel).getPose());
	}

}
