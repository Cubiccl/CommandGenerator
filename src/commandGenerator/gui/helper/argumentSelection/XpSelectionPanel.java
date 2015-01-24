package commandGenerator.gui.helper.argumentSelection;

import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class XpSelectionPanel extends HelperPanel
{

	private CEntry entryXp;
	private CCheckBox checkboxLevel;

	public XpSelectionPanel(String title)
	{
		super(CGConstants.PANELID_XP, title, 400, 50);

		entryXp = new CEntry(CGConstants.DATAID_VALUE, "GUI:xp.xp");

		checkboxLevel = new CCheckBox(CGConstants.DATAID_CHECK, "GUI:xp.levels");

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(entryXp, gbc);
		gbc.gridx++;
		add(checkboxLevel, gbc);
	}

	public String generateXp()
	{
		String xp = entryXp.getText();

		try
		{
			int flag = Integer.parseInt(xp);
			if (!checkboxLevel.isSelected() && flag <= 0)
			{
				DisplayHelper.warningInteger();
				return null;
			}
		} catch (Exception ex)
		{
			DisplayHelper.warningInteger();
			return null;
		}

		if (checkboxLevel.isSelected()) xp += "L";

		return xp;
	}

}
