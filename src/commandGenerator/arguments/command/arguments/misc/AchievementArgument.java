package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.Achievement;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.gui.helper.argumentSelection.AchievementSelectionPanel;

public class AchievementArgument extends Argument
{
	private AchievementSelectionPanel panel;

	public AchievementArgument(boolean isCompulsery)
	{
		super("achievement", isCompulsery);
	}

	@Override
	public String generateCommand()
	{
		return this.panel.getSelectedAchievement().commandStructure();
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
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

	@Override
	public boolean matches(List<String> data)
	{
		return Registry.exists(data.get(0), ObjectBase.ACHIEVEMENT);
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.panel.setSelected((Achievement) Registry.getObjectFromId(data.get(0)));
	}

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof AchievementArgument)) return;
		this.panel.setupFrom(((AchievementArgument) toMatch).panel.getSelectedAchievement());
	}

	@Override
	public void reset()
	{
		this.panel.reset();
	}

}
