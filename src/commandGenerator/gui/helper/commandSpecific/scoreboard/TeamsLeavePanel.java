package commandGenerator.gui.helper.commandSpecific.scoreboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JTextField;

import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.main.Constants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class TeamsLeavePanel extends ScoreboardPanel
{

	private CCheckBox checkboxTeam;
	private TargetSelectionPanel panelPlayer;
	private JTextField textfieldTeam;

	public TeamsLeavePanel()
	{
		super();

	}

	@Override
	protected void addComponents()
	{
		addLine(checkboxTeam, textfieldTeam);
		add(panelPlayer);
	}

	@Override
	protected void createComponents()
	{
		textfieldTeam = new JTextField(15);

		checkboxTeam = new CCheckBox(Constants.DATAID_NONE, "GUI:scoreboard.teams.leave");
		checkboxTeam.setSelected(true);

		panelPlayer = new TargetSelectionPanel(Constants.PANELID_TARGET, "GENERAL:target.entity", Constants.ENTITIES_ALL);
	}

	@Override
	protected void createListeners()
	{
		checkboxTeam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				textfieldTeam.setEnabled(checkboxTeam.isSelected());
			}
		});
	}

	@Override
	public String generateText()
	{
		Target player = panelPlayer.generateEntity();

		if ((textfieldTeam.getText().equals("") || textfieldTeam.getText().contains(" ")) && checkboxTeam.isSelected())
		{
			DisplayHelper.warningName();
			return null;
		}
		if (player == null) return null;

		String text = "";
		if (checkboxTeam.isSelected()) text += textfieldTeam.getText() + " ";
		return text + player.commandStructure();
	}

	public void setupFrom(Map<String, Object> data)
	{
		panelPlayer.setupFrom(data);
		if (data.get(Constants.DATAID_NAME) != null) textfieldTeam.setText((String) data.get(Constants.DATAID_NAME));
		checkboxTeam.setSelected(data.get(Constants.DATAID_NAME) != null);
		textfieldTeam.setEnabled(data.get(Constants.DATAID_NAME) != null);

	}

}
