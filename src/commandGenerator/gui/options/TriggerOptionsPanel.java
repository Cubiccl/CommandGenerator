package commandGenerator.gui.options;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.combobox.LangComboBox;
import commandGenerator.gui.helper.components.panel.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class TriggerOptionsPanel extends OptionsPanel
{
	private static final String[] modes = { "set", "add" };

	private CEntry entryName, entryValue;
	private LangComboBox comboboxMode;

	@Override
	public String generateCommand()
	{
		String value = entryValue.getText(), name = entryName.getText();

		if (name == null || name.equals("") || name.contains(" "))
		{
			DisplayHelper.warningName();
			return null;
		}

		try
		{
			int i = Integer.parseInt(value);
			if (comboboxMode.getSelectedIndex() == 0 && i < 0)
			{
				DisplayHelper.warningPositiveInteger();
				return null;
			}
		} catch (Exception e)
		{
			DisplayHelper.showWarning("WARNING:integer");
			return null;
		}

		return "trigger " + entryName.getText() + " " + modes[comboboxMode.getSelectedIndex()] + " " + value;
	}

	@Override
	protected void addComponents()
	{
		add(entryName);
		add(comboboxMode);
		add(entryValue);
	}

	@Override
	protected void createComponents()
	{
		entryName = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.objective", "");
		entryValue = new CEntry(CGConstants.DATAID_VALUE, "GUI:scoreboard.score", "0");

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:trigger.mode", 2);
	}

	@Override
	protected void createListeners()
	{}

}
