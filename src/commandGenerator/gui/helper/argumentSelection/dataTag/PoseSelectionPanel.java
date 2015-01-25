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

		List<Tag> list = new ArrayList<Tag>();
		if (tBody != null) list.add(tBody);
		if (tLArm != null) list.add(tLArm);
		if (tRArm != null) list.add(tRArm);
		if (tHead != null) list.add(tHead);
		if (tLLeg != null) list.add(tLLeg);
		if (tRLeg != null) list.add(tRLeg);
		return list;
	}

	@Override
	public void updateLang()
	{}

	public void setup(List<Tag> value)
	{
		for (int i = 0; i < value.size(); i++)
		{
			if (value.get(i).getId().equals("Body")) body.setup((TagList) value.get(i));
			if (value.get(i).getId().equals("LeftArm")) lArm.setup((TagList) value.get(i));
			if (value.get(i).getId().equals("RightArm")) rArm.setup((TagList) value.get(i));
			if (value.get(i).getId().equals("Head")) head.setup((TagList) value.get(i));
			if (value.get(i).getId().equals("LeftLeg")) lLeg.setup((TagList) value.get(i));
			if (value.get(i).getId().equals("RightLeg")) rLeg.setup((TagList) value.get(i));
		}
	}

}
