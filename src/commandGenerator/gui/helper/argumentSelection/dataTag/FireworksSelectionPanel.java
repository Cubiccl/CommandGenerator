package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.util.List;

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.gui.helper.components.spinner.NumberSpinner;

@SuppressWarnings("serial")
public class FireworksSelectionPanel extends CPanel
{

	private ListSelectionPanel explosionsPanel;
	private NumberSpinner spinnerFlight;

	public FireworksSelectionPanel(String title)
	{
		super(title);
		
		this.initGui();
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
		spinnerFlight = new NumberSpinner("GUI:fireworks.flight", -128, 127, null);
		spinnerFlight.setSelected(0);

		explosionsPanel = new ListSelectionPanel("TAGS:Explosions", ObjectBase.TAG_EXPLOSION);
	}

	@Override
	protected void createListeners()
	{}

	public TagList getExplosions()
	{

		TagList tags = new TagList("Explosions") {
			@Override
			public void askValue()
			{}
		};
		return tags.setValue(explosionsPanel.getList());

	}

	public int getFlight()
	{
		return spinnerFlight.getValue();
	}

	public void setup(int flight, List<Tag> list)
	{
		spinnerFlight.setSelected(flight);
		if (list != null) explosionsPanel.setList(list);
	}

}
