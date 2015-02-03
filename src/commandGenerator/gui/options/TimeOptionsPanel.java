package commandGenerator.gui.options;

import java.awt.Dimension;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class TimeOptionsPanel extends OptionsPanel
{

	private static final String[] modes = { "set", "add" };
	private LangComboBox comboboxMode;
	private CEntry entryTime;
	private CLabel labelMode;

	public TimeOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		addLine(labelMode, comboboxMode);
		add(entryTime);
	}

	@Override
	protected void createComponents()
	{
		labelMode = new CLabel("GUI:time.mode");

		entryTime = new CEntry(CGConstants.DATAID_VALUE, "GUI:time.time");

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:time.mode", 2);
		comboboxMode.setPreferredSize(new Dimension(200, 20));
	}

	@Override
	protected void createListeners()
	{}

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
