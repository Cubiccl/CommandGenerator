package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.components.panel.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class WorldspawnOptionsPanel extends OptionsPanel
{

	private CoordSelectionPanel panelCoords;

	public WorldspawnOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelCoords);
	}

	@Override
	protected void createComponents()
	{
		panelCoords = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:worldspawn.coords", true, false);
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{
		Coordinates coords = panelCoords.generateCoord();

		if (coords == null) return null;

		return "setworldspawn " + coords.commandStructure();
	}

}
