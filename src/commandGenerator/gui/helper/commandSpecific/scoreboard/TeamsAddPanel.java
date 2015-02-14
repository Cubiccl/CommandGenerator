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

	private CCheckBox checkboxDisplay;
	private CEntry entryName;
	private JTextField textfieldDisplay;

	public TeamsAddPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(entryName);
		addLine(checkboxDisplay, textfieldDisplay);
	}

	@Override
	protected void createComponents()
	{
		entryName = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.team", "");

		textfieldDisplay = new JTextField(15);
		textfieldDisplay.setEnabled(false);

		checkboxDisplay = new CCheckBox(CGConstants.DATAID_NONE, "GUI:scoreboard.display");
	}

	@Override
	protected void createListeners()
	{
		checkboxDisplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				textfieldDisplay.setEnabled(checkboxDisplay.isSelected());
			}
		});
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
