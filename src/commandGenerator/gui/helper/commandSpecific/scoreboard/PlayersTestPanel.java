package commandGenerator.gui.helper.commandSpecific.scoreboard;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JTextField;

import commandGenerator.arguments.objects.EntitySelector;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class PlayersTestPanel extends ScoreboardPanel
{

	private CEntry entryObjective, entryScore;
	private JTextField textfieldMax;
	private CCheckBox checkboxMax;
	private EntitySelectionPanel panelEntity;

	public PlayersTestPanel()
	{
		super();
		setPreferredSize(new Dimension(500, 300));

		entryObjective = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.objective");
		entryScore = new CEntry(CGConstants.DATAID_VALUE, "GUI:scoreboard.score.min");

		textfieldMax = new JTextField(15);
		textfieldMax.setEnabled(false);

		checkboxMax = new CCheckBox(CGConstants.DATAID_NONE, "GUI:scoreboard.score.max");
		checkboxMax.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				textfieldMax.setEnabled(checkboxMax.isSelected());
			}
		});

		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(entryObjective, gbc);
		gbc.gridy++;
		add(entryScore, gbc);
		gbc.gridwidth = 1;

		gbc.gridy++;
		add(checkboxMax, gbc);
		gbc.gridx++;
		add(textfieldMax, gbc);

		gbc.gridx--;
		gbc.gridy++;
		gbc.gridwidth = 2;
		add(panelEntity, gbc);
		gbc.gridwidth = 1;
	}

	@Override
	public String generateText()
	{

		EntitySelector player = panelEntity.generateEntity();
		String objective = entryObjective.getText(), score = entryScore.getText(), max = textfieldMax.getText();

		if (objective.equals("") || objective.contains(" "))
		{
			DisplayHelper.warningName();
			return null;
		}

		try
		{
			@SuppressWarnings("unused")
			int test = Integer.parseInt(score);
		} catch (Exception ex)
		{
			DisplayHelper.warningPositiveInteger();
			return null;
		}

		if (checkboxMax.isSelected())
		{
			try
			{
				int test = Integer.parseInt(max);
				int test2 = Integer.parseInt(score);
				if (test < test2)
				{
					DisplayHelper.showWarning("WARNING:scoreboard.greater");
					return null;
				}
			} catch (Exception ex)
			{
				DisplayHelper.warningPositiveInteger();
				return null;
			}
		}

		if (player == null) return null;

		String text = player.commandStructure() + " " + objective + " " + score;
		if (checkboxMax.isSelected()) text += " " + max;
		return text;

	}

	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		checkboxMax.setSelected(data.get(CGConstants.DATAID_CHECK) != null);
		textfieldMax.setEnabled(data.get(CGConstants.DATAID_CHECK) != null);
		textfieldMax.setText((String) data.get(CGConstants.DATAID_CHECK));
	}

}
