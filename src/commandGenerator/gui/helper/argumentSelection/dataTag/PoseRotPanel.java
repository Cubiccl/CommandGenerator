package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagFloat;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.gui.helper.components.spinner.NumberSpinner;

@SuppressWarnings("serial")
public class PoseRotPanel extends CPanel
{

	private String id;
	private NumberSpinner spinnerX, spinnerY, spinnerZ;

	public PoseRotPanel(String name, String id)
	{
		super(name);
		this.id = id;
		
		this.initGui();
	}

	@Override
	protected void addComponents()
	{
		add(spinnerX);
		add(spinnerY);
		add(spinnerZ);
	}

	@Override
	protected void createComponents()
	{
		spinnerX = new NumberSpinner("GUI:rotation.x", -128, 127, null);
		spinnerY = new NumberSpinner("GUI:rotation.y", -128, 127, null);
		spinnerZ = new NumberSpinner("GUI:rotation.z", -128, 127, null);

		spinnerX.setSelected(0);
		spinnerY.setSelected(0);
		spinnerZ.setSelected(0);
	}

	@Override
	protected void createListeners()
	{}

	public TagList getPose()
	{
		if (spinnerX.getValue() == 0 && spinnerY.getValue() == 0 && spinnerZ.getValue() == 0) return null;

		List<Tag> list = new ArrayList<Tag>();
		TagList tag = new TagList(this.id) {
			@Override
			public void askValue()
			{}
		};
		list.add(new TagFloat().setValue(spinnerX.getValue()));
		list.add(new TagFloat().setValue(spinnerY.getValue()));
		list.add(new TagFloat().setValue(spinnerZ.getValue()));
		return tag.setValue(list);
	}

	public void setup(TagList tag)
	{
		int x = (int) ((TagFloat) tag.get(0)).getValue();
		int y = (int) ((TagFloat) tag.get(1)).getValue();
		int z = (int) ((TagFloat) tag.get(2)).getValue();
		spinnerX.setSelected(x);
		spinnerY.setSelected(y);
		spinnerZ.setSelected(z);
	}

}
