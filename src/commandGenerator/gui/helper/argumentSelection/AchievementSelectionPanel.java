package commandGenerator.gui.helper.argumentSelection;

import javax.swing.JLabel;

import commandGenerator.arguments.objects.Achievement;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.gui.helper.components.combobox.CComboBox;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class AchievementSelectionPanel extends HelperPanel implements IBox
{

	private CComboBox achievementBox;
	private JLabel image;

	public AchievementSelectionPanel()
	{
		super("GUI:achievement.title");

		this.initGui();
	}

	@Override
	protected void addComponents()
	{
		addLine(achievementBox, image);
	}

	@Override
	protected void createComponents()
	{
		image = new JLabel();
		achievementBox = new CComboBox("GUI:achievement.select", Registry.getObjectList(ObjectBase.ACHIEVEMENT), this);
		updateCombobox();
	}

	@Override
	protected void createListeners()
	{}

	public Achievement getSelectedAchievement()
	{
		DisplayHelper.log("Generated achievement " + achievementBox.getValue().getName());
		return (Achievement) achievementBox.getValue();
	}

	public void setSelected(Achievement achievement)
	{
		this.achievementBox.setSelected(achievement);
	}

	public void setupFrom(Achievement achievement)
	{
		if (achievement != null) this.achievementBox.setSelected(achievement);
	}

	@Override
	public void updateCombobox()
	{
		Achievement achievement = (Achievement) achievementBox.getValue();
		if (achievement != null) image.setIcon(achievement.getTexture());
	}

}
