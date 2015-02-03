package commandGenerator.gui.helper.commandSpecific;

import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class SpreadPanel extends HelperPanel
{

	private CCheckBox checkboxRespectTeams;
	private CEntry entryX, entryZ, entryDistance, entryDistanceMax;

	public SpreadPanel(String title)
	{
		super(CGConstants.PANELID_NONE, title);
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
		entryX = new CEntry(CGConstants.DATAID_SPREAD_X, "GUI:spread.x");
		entryZ = new CEntry(CGConstants.DATAID_SPREAD_Z, "GUI:spread.z");
		entryDistance = new CEntry(CGConstants.DATAID_SPREAD_DIST, "GUI:spread.distance");
		entryDistanceMax = new CEntry(CGConstants.DATAID_SPREAD_DISTMAX, "GUI:spread.distance_max");

		checkboxRespectTeams = new CCheckBox(CGConstants.DATAID_CHECK, "GUI:spread.respect");
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
