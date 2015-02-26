package commandGenerator.gui.helper.commandSpecific;

import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.Constants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class SpreadPanel extends HelperPanel
{

	private CCheckBox checkboxRespectTeams;
	private CEntry entryX, entryZ, entryDistance, entryDistanceMax;

	public SpreadPanel(String title)
	{
		super(Constants.PANELID_NONE, title);
	}

	@Override
	protected void addComponents()
	{
		add(entryX);
		add(entryZ);
		add(entryDistance);
		add(entryDistanceMax);
		add(checkboxRespectTeams);
	}

	@Override
	protected void createComponents()
	{
		entryX = new CEntry(Constants.DATAID_SPREAD_X, "GUI:spread.x", "0");
		entryZ = new CEntry(Constants.DATAID_SPREAD_Z, "GUI:spread.z", "0");
		entryDistance = new CEntry(Constants.DATAID_SPREAD_DIST, "GUI:spread.distance", "10");
		entryDistanceMax = new CEntry(Constants.DATAID_SPREAD_DISTMAX, "GUI:spread.distance_max", "20");

		checkboxRespectTeams = new CCheckBox(Constants.DATAID_CHECK, "GUI:spread.respect");
	}

	@Override
	protected void createListeners()
	{}

	public String[] generateOptions()
	{
		DisplayHelper.log("Generating /spreadplayers options");
		String x = entryX.getText();
		String z = entryZ.getText();
		String distance = entryDistance.getText();
		String distanceMax = entryDistanceMax.getText();

		try
		{
			int testx = Integer.parseInt(x);
			int testz = Integer.parseInt(z);
			int testd = Integer.parseInt(distance);
			int testm = Integer.parseInt(distanceMax);
			if (testx < 0 || testz < 0 || testd < 0 || testm < 0)
			{
				DisplayHelper.warningPositiveInteger();
				return null;
			}
		} catch (Exception ex)
		{
			DisplayHelper.warningPositiveInteger();
			return null;
		}

		if (checkboxRespectTeams.isSelected()) return new String[] { x, z, distance, distanceMax, "true" };
		else return new String[] { x, z, distance, distanceMax, "false" };
	}

}
