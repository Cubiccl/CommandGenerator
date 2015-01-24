package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class PoseSelectionPanel extends HelperPanel
{

	private PoseRotPanel body, lArm, rArm, head, lLeg, rLeg;

	public PoseSelectionPanel()
	{
		super(CGConstants.DATAID_NONE, "TAGS:Pose", 1000, 500);

		body = new PoseRotPanel("GUI:pose.body", "Body");
		lArm = new PoseRotPanel("GUI:pose.arm_left", "LeftArm");
		rArm = new PoseRotPanel("GUI:pose.arm_right", "RightArm");
		head = new PoseRotPanel("GUI:pose.head", "Head");
		lLeg = new PoseRotPanel("GUI:pose.leg_left", "LeftLeg");
		rLeg = new PoseRotPanel("GUI:pose.leg_right", "RightLeg");

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(body, gbc);
		gbc.gridx++;
		add(lArm, gbc);
		gbc.gridx++;
		add(rArm, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		add(head, gbc);
		gbc.gridx++;
		add(lLeg, gbc);
		gbc.gridx++;
		add(rLeg, gbc);
	}

	public List<Tag> getPose()
	{
		TagList tBody = body.getPose();
		TagList tLArm = lArm.getPose();
		TagList tRArm = rArm.getPose();
		TagList tHead = head.getPose();
		TagList tLLeg = lLeg.getPose();
		TagList tRLeg = rLeg.getPose();

		if (tBody == null || tLArm == null || tRArm == null || tHead == null || tLLeg == null || tRLeg == null) return null;

		List<Tag> list = new ArrayList<Tag>();
		list.add(tBody);
		list.add(tLArm);
		list.add(tRArm);
		list.add(tHead);
		list.add(tLLeg);
		list.add(tRLeg);
		return list;
	}

	@Override
	public void updateLang()
	{}

}
