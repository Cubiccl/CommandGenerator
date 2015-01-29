package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagFloat;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class DropChancesSelectionPanel extends HelperPanel
{

	private CLabel label;
	private CEntry entryHand, entryHead, entryChest, entryLegs, entryFeet;

	public DropChancesSelectionPanel(String title)
	{
		super(CGConstants.DATAID_NONE, title, 600, 200);

		label = new CLabel("GUI:desc.drop_chances");

		entryHand = new CEntry(CGConstants.DATAID_NONE, "GUI:slot.hand");
		entryHead = new CEntry(CGConstants.DATAID_NONE, "GUI:slot.head");
		entryChest = new CEntry(CGConstants.DATAID_NONE, "GUI:slot.chest");
		entryLegs = new CEntry(CGConstants.DATAID_NONE, "GUI:slot.legs");
		entryFeet = new CEntry(CGConstants.DATAID_NONE, "GUI:slot.feet");

		entryHand.setTextField("0");
		entryHead.setTextField("0");
		entryChest.setTextField("0");
		entryLegs.setTextField("0");
		entryFeet.setTextField("0");

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(label);
		gbc.gridy++;
		add(entryHand);
		gbc.gridy++;
		add(entryHead);
		gbc.gridy++;
		add(entryChest);
		gbc.gridy++;
		add(entryLegs);
		gbc.gridy++;
		add(entryFeet);
	}

	public List<Tag> getDropChances()
	{

		String hand = entryHand.getText();
		String head = entryHead.getText();
		String chest = entryChest.getText();
		String legs = entryLegs.getText();
		String feet = entryFeet.getText();

		try
		{
			float testHa = Float.parseFloat(hand);
			float testHe = Float.parseFloat(head);
			float testC = Float.parseFloat(chest);
			float testL = Float.parseFloat(legs);
			float testF = Float.parseFloat(feet);
			if (testHa < 0.0f || testHe < 0.0f || testC < 0.0f || testL < 0.0f || testF < 0.0f || testHa > 1.0f || testHe > 1.0f || testC > 1.0f
					|| testL > 1.0f || testF > 1.0f)
			{
				DisplayHelper.warningInteger(0, 1);
				return null;
			}
		} catch (Exception ex)
		{
			DisplayHelper.warningInteger(0, 1);
			return null;
		}

		List<Tag> tag = new ArrayList<Tag>();
		tag.add(new TagFloat().setValue(Float.parseFloat(hand)));
		tag.add(new TagFloat().setValue(Float.parseFloat(feet)));
		tag.add(new TagFloat().setValue(Float.parseFloat(legs)));
		tag.add(new TagFloat().setValue(Float.parseFloat(chest)));
		tag.add(new TagFloat().setValue(Float.parseFloat(head)));
		return tag;
	}

	@Override
	public void updateLang()
	{
		updateTitle();
		label.updateLang();
		entryChest.updateLang();
		entryFeet.updateLang();
		entryHand.updateLang();
		entryHead.updateLang();
		entryLegs.updateLang();
	}

	public void setup(List<Tag> data)
	{
		if (data.size() < 5) DisplayHelper.log("Error : missing drop chances");

		float[] chances = { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f };
		for (int i = 0; i < data.size() && i < 5; i++)
			chances[i] = ((TagFloat) data.get(i)).getValue();

		entryHand.setTextField(Float.toString(chances[0]));
		entryFeet.setTextField(Float.toString(chances[1]));
		entryLegs.setTextField(Float.toString(chances[2]));
		entryChest.setTextField(Float.toString(chances[3]));
		entryHead.setTextField(Float.toString(chances[4]));
	}

}
