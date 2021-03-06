package commandGenerator.gui.helper.argumentSelection;

import javax.swing.JLabel;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.Achievement;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.gui.helper.components.combobox.ObjectComboBox;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class AchievementSelectionPanel extends CPanel implements IBox
{

	private ObjectComboBox achievementBox;
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
		achievementBox = new ObjectComboBox("GUI:achievement.select", Generator.registry.getObjectList(ObjectBase.ACHIEVEMENT), this);
		updateCombobox();
	}

	@Override
	protected void createListeners()
	{}

	public Achievement getSelectedAchievement()
	{
		if (this.achievementBox.getValue() == null) return null;
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
