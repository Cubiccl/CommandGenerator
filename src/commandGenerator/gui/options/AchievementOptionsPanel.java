package commandGenerator.gui.options;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComboBox;

import commandGenerator.arguments.objects.Achievement;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.AchievementSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class AchievementOptionsPanel extends OptionsPanel
{

	private static CCheckBox checkbox;
	private static JComboBox<String> comboboxMode;
	private static AchievementSelectionPanel panelAchievement;
	private static EntitySelectionPanel panelTarget;

	public AchievementOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(comboboxMode);
		add(checkbox);
		add(panelTarget);
		add(panelAchievement);
	}

	@Override
	protected void createComponents()
	{
		checkbox = new CCheckBox(CGConstants.DATAID_CHECK, "GUI:achievement.all");

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "GUI:achievement.mode", 2);
		comboboxMode.setPreferredSize(new Dimension(200, 20));

		panelTarget = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_PLAYERS);

		panelAchievement = new AchievementSelectionPanel();
	}

	@Override
	protected void createListeners()
	{
		checkbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				panelAchievement.setEnabledContent(!checkbox.isSelected());
			}
		});
	}

	@Override
	public String generateCommand()
	{

		Target player = panelTarget.generateEntity();
		Achievement achievement = panelAchievement.getSelectedAchievement();

		if (player == null || achievement == null) return null;

		String command = "achievement give ";
		if (comboboxMode.getSelectedIndex() == 1) command = "achievement take ";

		if (!checkbox.isSelected()) command += achievement.commandStructure();
		else command += "*";

		return command + " " + player.commandStructure();
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		panelAchievement.setEnabledContent(!(boolean) data.get(CGConstants.DATAID_CHECK));
	}

}
