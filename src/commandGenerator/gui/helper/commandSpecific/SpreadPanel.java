package commandGenerator.gui.helper.commandSpecific;

import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class SpreadPanel extends HelperPanel
{

	private CEntry entryX, entryZ, entryDistance, entryDistanceMax;
	private CCheckBox checkboxRespectTeams;

	public SpreadPanel(String title)
	{
		super(CGConstants.PANELID_NONE, title, 400, 200);

		entryX = new CEntry(CGConstants.DATAID_SPREAD_X, "GUI:spread.x");
		entryZ = new CEntry(CGConstants.DATAID_SPREAD_Z, "GUI:spread.z");
		entryDistance = new CEntry(CGConstants.DATAID_SPREAD_DIST, "GUI:spread.distance");
		entryDistanceMax = new CEntry(CGConstants.DATAID_SPREAD_DISTMAX, "GUI:spread.distance_max");

		checkboxRespectTeams = new CCheckBox(CGConstants.DATAID_CHECK, "GUI:spread.respect");

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(entryX, gbc);
		gbc.gridy++;
		add(entryZ, gbc);
		gbc.gridy++;
		add(entryDistance, gbc);
		gbc.gridy++;
		add(entryDistanceMax, gbc);
		gbc.gridy++;
		add(checkboxRespectTeams, gbc);
	}

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
