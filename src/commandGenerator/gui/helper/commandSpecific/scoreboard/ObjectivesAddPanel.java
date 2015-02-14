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
public class ObjectivesAddPanel extends ScoreboardPanel
{

	private CCheckBox checkboxDisplay;
	private CEntry entryName;
	private ObjectiveSelectionPanel panelCriteria;
	private JTextField textfieldDisplay;

	public ObjectivesAddPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(entryName);
		add(panelCriteria);
		addLine(checkboxDisplay, textfieldDisplay);
	}

	@Override
	protected void createComponents()
	{
		entryName = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.objective", "");

		textfieldDisplay = new JTextField(20);
		textfieldDisplay.setEnabled(false);

		checkboxDisplay = new CCheckBox(CGConstants.DATAID_NONE, "GUI:scoreboard.display");

		panelCriteria = new ObjectiveSelectionPanel("GUI:scoreboard.objective.type");
	}

	@Override
	protected void createListeners()
	{
		checkboxDisplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				textfieldDisplay.setEnabled(checkboxDisplay.isSelected());
			}
		});
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
