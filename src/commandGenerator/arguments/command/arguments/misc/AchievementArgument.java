package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.argumentSelection.AchievementSelectionPanel;
import commandGenerator.main.CGConstants;

public class AchievementArgument extends Argument
{
	private AchievementSelectionPanel panel;

	public AchievementArgument(boolean isCompulsery)
	{
		super(CGConstants.PANELID_ACHIEVEMENT, Argument.ACHIEVEMENT, isCompulsery, 1);
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public String generateCommand()
	{
		return this.panel.getSelectedAchievement().commandStructure();
	}

	@Override
	public void initGui()
	{
		this.panel = new AchievementSelectionPanel();
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

}
