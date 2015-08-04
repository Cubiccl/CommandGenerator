package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagFloat;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class DropChancesSelectionPanel extends CPanel
{

	private String[] slots;
	private CEntry[] entries;
	private CLabel label;

	public DropChancesSelectionPanel(String title, String... slots)
	{
		super(title);
		this.slots = slots;
		this.entries = new CEntry[this.slots.length];

		this.initGui();
	}

	@Override
	protected void addComponents()
	{
		this.add(this.label);
		for (int i = 0; i < this.entries.length; i++)
		{
			this.add(this.entries[i]);
		}
	}

	@Override
	protected void createComponents()
	{
		this.label = new CLabel("GUI:desc.drop_chances");

		for (int i = 0; i < this.slots.length; i++)
		{
			this.entries[i] = new CEntry("GUI:slot." + this.slots[i], "1.0");
			this.entries[i].setMinimumSize(new Dimension(200, 20));
		}
	}

	@Override
	protected void createListeners()
	{}

	public List<Tag> getDropChances()
	{

		List<Tag> tag = new ArrayList<Tag>();

		for (int i = 0; i < this.entries.length; i++)
		{
			String text = this.entries[i].getText();

			try
			{
				float test = Float.parseFloat(text);
				if (test < 0.0f || test > 1.0f)
				{
					DisplayHelper.warningBounds(0, 1);
					return null;
				}
			} catch (Exception ex)
			{
				DisplayHelper.warningBounds(0, 1);
				return null;
			}
			tag.add(new TagFloat().setValue(Float.parseFloat(text)));
		}
		return tag;
	}

	public void setup(List<Tag> data)
	{
		if (data.size() < this.slots.length) DisplayHelper.log("Error : missing drop chances");

		float[] chances = new float[this.slots.length];
		for (int i = 0; i < chances.length; i++)
			chances[i] = 0.0f;

		for (int i = 0; i < data.size() && i < this.slots.length; i++)
		{
			chances[i] = ((TagFloat) data.get(i)).getValue();
			this.entries[i].setTextField(Float.toString(chances[i]));
		}
	}

}
