package commandGenerator.gui.helper.commandSpecific.scoreboard;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JTextField;

import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class ObjectivesAddPanel extends ScoreboardPanel
{

	private CEntry entryName;
	private JTextField textfieldDisplay;
	private CCheckBox checkboxDisplay;
	private ObjectiveSelectionPanel panelCriteria;

	public ObjectivesAddPanel()
	{
		super();
		setPreferredSize(new Dimension(700, 300));

		entryName = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.objective");

		textfieldDisplay = new JTextField(20);
		textfieldDisplay.setEnabled(false);

		checkboxDisplay = new CCheckBox(CGConstants.DATAID_NONE, "GUI:scoreboard.display");
		checkboxDisplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				textfieldDisplay.setEnabled(checkboxDisplay.isSelected());
			}
		});

		panelCriteria = new ObjectiveSelectionPanel("GUI:scoreboard.objective.type");

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(entryName, gbc);
		gbc.gridy++;
		add(panelCriteria, gbc);
		gbc.gridwidth = 1;
		gbc.gridy++;
		add(checkboxDisplay, gbc);
		gbc.gridx++;
		add(textfieldDisplay, gbc);
	}

	@Override
	public String generateText()
	{

		if (entryName.getText().equals("") || entryName.getText().contains(" ")
				|| ((textfieldDisplay.getText().equals("") || textfieldDisplay.getText().contains(" ")) && checkboxDisplay.isSelected()))
		{
			DisplayHelper.warningName();
			return null;
		}

		String text = entryName.getText() + " " + panelCriteria.generateObjective();
		if (checkboxDisplay.isSelected()) text += " " + textfieldDisplay.getText();
		return text;
	}

	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		checkboxDisplay.setSelected(data.get(CGConstants.DATAID_NAME2) != null);
		textfieldDisplay.setEnabled(data.get(CGConstants.DATAID_NAME2) != null);
		textfieldDisplay.setText((String) data.get(CGConstants.DATAID_NAME2));
	}

}
