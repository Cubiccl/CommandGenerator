package commandGenerator.gui.helper.commandSpecific.scoreboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JTextField;

import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class TeamsAddPanel extends ScoreboardPanel
{

	private CEntry entryName;
	private JTextField textfieldDisplay;
	private CCheckBox checkboxDisplay;

	public TeamsAddPanel()
	{
		super();

		entryName = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.team");

		textfieldDisplay = new JTextField(15);
		textfieldDisplay.setEnabled(false);

		checkboxDisplay = new CCheckBox(CGConstants.DATAID_NONE, "GUI:scoreboard.display");
		checkboxDisplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				textfieldDisplay.setEnabled(checkboxDisplay.isSelected());
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(entryName);
		gbc.gridwidth = 1;
		gbc.gridy++;
		add(checkboxDisplay);
		gbc.gridx++;
		add(textfieldDisplay);
	}

	@Override
	public String generateText()
	{
		if (entryName.getText().equals("") || entryName.getText().contains(" ")
				|| (checkboxDisplay.isSelected() && (textfieldDisplay.getText().equals("") || entryName.getText().contains(" "))))
		{
			DisplayHelper.warningName();
			return null;
		}
		String text = entryName.getText();
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
