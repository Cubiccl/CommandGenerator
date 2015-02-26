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

	private ListSelectionPanel explosionsPanel;
	private NumberSpinner spinnerFlight;

	public FireworksSelectionPanel(String title)
	{
		super(CGConstants.DATAID_NONE, title);
	}

	@Override
	protected void addComponents()
	{
		add(spinnerFlight);
		add(explosionsPanel);
	}

	@Override
	protected void createComponents()
	{
		spinnerFlight = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:fireworks.flight", -128, 127, null);
		spinnerFlight.setSelected(0);

		explosionsPanel = new ListSelectionPanel("TAGS:Explosions", CGConstants.OBJECT_TAG_EXPLOSION);
	}

	@Override
	protected void createListeners()
	{}

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

	public void setup(int flight, List<Tag> list)
	{
		spinnerFlight.setSelected(flight);
		if (list != null) explosionsPanel.setList(list);
	}

	@Override
	public void updateLang()
	{
		updateTitle();
		spinnerFlight.updateLang();
		explosionsPanel.updateLang();
	}

}
