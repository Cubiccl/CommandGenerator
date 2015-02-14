package commandGenerator.gui.helper.commandSpecific.scoreboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Resources;

@SuppressWarnings("serial")
public class ObjectivesSetdisplayPanel extends ScoreboardPanel
{

	private CCheckBox checkboxClear;
	private LangComboBox comboboxDisplay;
	private CEntry entryName;
	private CLabel labelDisplay;

	public ObjectivesSetdisplayPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		addLine(labelDisplay, comboboxDisplay);
		add(checkboxClear);
		add(entryName);
	}

	@Override
	protected void createComponents()
	{
		labelDisplay = new CLabel("GUI:scoreboard.slot");

		checkboxClear = new CCheckBox(CGConstants.DATAID_NONE, "GUI:scoreboard.slot.clear");

		entryName = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.objective", "");

		comboboxDisplay = new LangComboBox(CGConstants.DATAID_VALUE, "RESOURCES:scoreboard.slot", 19);
	}

	@Override
	protected void createListeners()
	{
		checkboxClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				entryName.setEnabledContent(!checkboxClear.isSelected());
			}
		});
	}

	@Override
	public String generateText()
	{
		if (entryName.getText().contains(" "))
		{
			DisplayHelper.warningName();
			return null;
		}
		if (!checkboxClear.isSelected()) return Resources.displayList[comboboxDisplay.getSelectedIndex()] + " " + entryName.getText();
		return Resources.displayList[comboboxDisplay.getSelectedIndex()];
	}

	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		checkboxClear.setSelected(data.get(CGConstants.DATAID_NAME) == null);
		entryName.setEnabledContent(data.get(CGConstants.DATAID_NAME) != null);
	}

}
