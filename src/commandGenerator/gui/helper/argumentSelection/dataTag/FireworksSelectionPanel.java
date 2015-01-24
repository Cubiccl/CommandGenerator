package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.util.List;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.NumberSpinner;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class FireworksSelectionPanel extends HelperPanel
{

	private NumberSpinner spinnerFlight;
	private ListSelectionPanel explosionsPanel;

	public FireworksSelectionPanel(String title)
	{
		super(CGConstants.DATAID_NONE, title, 710, 300);

		spinnerFlight = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:fireworks.flight", -128, 127, null);
		spinnerFlight.setSelected(0);

		explosionsPanel = new ListSelectionPanel("TAGS:Explosions", CGConstants.OBJECT_TAG_EXPLOSION);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(spinnerFlight, gbc);
		gbc.gridy++;
		add(explosionsPanel, gbc);
	}

	public TagList getExplosions()
	{

		TagList tags = new TagList("Explosions") {
			public void askValue()
			{}
		};
		return tags.setValue(explosionsPanel.getList());

	}

	public int getFlight()
	{
		return (int) spinnerFlight.getValue();
	}

	@Override
	public void updateLang()
	{
		updateTitle();
		spinnerFlight.updateLang();
		explosionsPanel.updateLang();
	}

	public void setup(int flight, List<Tag> list)
	{
		spinnerFlight.setSelected(flight);
		if (list != null) explosionsPanel.setList(list);
	}

}
