package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagFloat;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.NumberSpinner;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class PoseRotPanel extends HelperPanel
{

	private String id;
	private NumberSpinner spinnerX, spinnerY, spinnerZ;

	public PoseRotPanel(String name, String id)
	{
		super(CGConstants.DATAID_NONE, name, 300, 150);

		this.id = id;

		spinnerX = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:rotation.x", -128, 127, null);
		spinnerY = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:rotation.y", -128, 127, null);
		spinnerZ = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:rotation.z", -128, 127, null);

		spinnerX.setSelected(0);
		spinnerY.setSelected(0);
		spinnerZ.setSelected(0);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(spinnerX, gbc);
		gbc.gridy++;
		add(spinnerY, gbc);
		gbc.gridy++;
		add(spinnerZ, gbc);
	}

	public TagList getPose()
	{

		List<Tag> list = new ArrayList<Tag>();
		TagList tag = new TagList(this.id) {
			public void askValue()
			{}
		};
		list.add(new TagFloat().setValue(spinnerX.getValue()));
		list.add(new TagFloat().setValue(spinnerX.getValue()));
		list.add(new TagFloat().setValue(spinnerX.getValue()));
		return tag.setValue(list);
	}

	@Override
	public void updateLang()
	{
		spinnerX.updateLang();
		spinnerY.updateLang();
		spinnerZ.updateLang();
	}

}
