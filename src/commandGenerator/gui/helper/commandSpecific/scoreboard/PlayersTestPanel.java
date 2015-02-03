package commandGenerator.gui.helper.commandSpecific.scoreboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JTextField;

import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class PlayersTestPanel extends ScoreboardPanel
{

	private CCheckBox checkboxMax;
	private CEntry entryObjective, entryScore;
	private EntitySelectionPanel panelEntity;
	private JTextField textfieldMax;

	public PlayersTestPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(entryObjective);
		add(entryScore);
		addLine(checkboxMax, textfieldMax);
		add(panelEntity);
	}

	@Override
	protected void createComponents()
	{
		entryObjective = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.objective");
		entryScore = new CEntry(CGConstants.DATAID_VALUE, "GUI:scoreboard.score.min");

		textfieldMax = new JTextField(15);
		textfieldMax.setEnabled(false);

		checkboxMax = new CCheckBox(CGConstants.DATAID_NONE, "GUI:scoreboard.score.max");

		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);
	}

	@Override
	protected void createListeners()
	{
		checkboxMax.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				textfieldMax.setEnabled(checkboxMax.isSelected());
			}
		});
	}

	@Override
	public String generateText()
	{

		Target player = panelEntity.generateEntity();
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
