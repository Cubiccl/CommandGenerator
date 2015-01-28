package commandGenerator.gui.helper.commandSpecific.scoreboard;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JTextField;

import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class PlayersResetPanel extends ScoreboardPanel
{

	private JTextField textfieldObjective;
	private CCheckBox checkboxObjective;
	private EntitySelectionPanel panelEntity;

	public PlayersResetPanel()
	{
		super();
		setPreferredSize(new Dimension(500, 300));

		textfieldObjective = new JTextField(15);

		checkboxObjective = new CCheckBox(CGConstants.DATAID_NONE, "GUI:scoreboard.players.clear.objective");
		checkboxObjective.setSelected(true);
		checkboxObjective.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				textfieldObjective.setEnabled(checkboxObjective.isSelected());
			}
		});

		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(checkboxObjective, gbc);
		gbc.gridx++;
		add(textfieldObjective, gbc);

		gbc.gridx--;
		gbc.gridy++;
		gbc.gridwidth = 2;
		add(panelEntity, gbc);
		gbc.gridwidth = 2;
	}

	@Override
	public String generateText()
	{

		Target player = panelEntity.generateEntity();
		String objective = textfieldObjective.getText();

		if ((objective.equals("") || objective.contains(" ")) && checkboxObjective.isSelected())
		{
			DisplayHelper.warningName();
			return null;
		}

		if (player == null) return null;

		String text = player.commandStructure();
		if (checkboxObjective.isSelected()) text += " " + objective;
		return text;

	}

	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		checkboxObjective.setSelected(data.get(CGConstants.DATAID_NAME) != null);
		textfieldObjective.setEnabled(data.get(CGConstants.DATAID_NAME) != null);
		textfieldObjective.setText((String) data.get(CGConstants.DATAID_NAME));
	}

}
