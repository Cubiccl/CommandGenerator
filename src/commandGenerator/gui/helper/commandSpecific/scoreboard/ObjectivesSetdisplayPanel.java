package commandGenerator.gui.helper.commandSpecific.scoreboard;

import java.awt.Dimension;
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

	private CLabel labelDisplay;
	private CCheckBox checkboxClear;
	private CEntry entryName;
	private LangComboBox comboboxDisplay;

	public ObjectivesSetdisplayPanel()
	{
		super();

		labelDisplay = new CLabel("GUI:scoreboard.slot");

		checkboxClear = new CCheckBox(CGConstants.DATAID_NONE, "GUI:scoreboard.slot.clear");
		checkboxClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				entryName.setEnabledContent(!checkboxClear.isSelected());
			}
		});

		entryName = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.objective");

		comboboxDisplay = new LangComboBox(CGConstants.DATAID_VALUE, "RESOURCES:scoreboard.slot", 19);
		comboboxDisplay.setPreferredSize(new Dimension(200, 20));

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(labelDisplay);
		gbc.gridx++;
		add(comboboxDisplay);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		add(checkboxClear);
		gbc.gridy++;
		add(entryName);
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
