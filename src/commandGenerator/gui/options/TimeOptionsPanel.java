package commandGenerator.gui.options;

import java.awt.Dimension;

import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class TimeOptionsPanel extends OptionsPanel
{

	private CLabel labelMode;
	private CEntry entryTime;
	private LangComboBox comboboxMode;
	private static final String[] modes = { "set", "add" };

	public TimeOptionsPanel()
	{
		super();

		labelMode = new CLabel("GUI:time.mode");

		entryTime = new CEntry(CGConstants.DATAID_VALUE, "GUI:time.time");

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:time.mode", 2);
		comboboxMode.setPreferredSize(new Dimension(200, 20));

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(labelMode, gbc);
		gbc.gridx++;
		add(comboboxMode, gbc);
		gbc.gridx--;
		gbc.gridy++;
		gbc.gridwidth = 2;
		add(entryTime, gbc);
		gbc.gridwidth = 1;
	}

	@Override
	public String generateCommand()
	{
		String time = entryTime.getText();

		try
		{
			int flag = Integer.parseInt(time);
			if (flag < 0)
			{
				DisplayHelper.warningPositiveInteger();
				return null;
			}
		} catch (Exception ex)
		{
			DisplayHelper.warningPositiveInteger();
			return null;
		}

		return "time " + modes[comboboxMode.getSelectedIndex()] + " " + time;
	}

}
