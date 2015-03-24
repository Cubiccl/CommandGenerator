package commandGenerator.gui.helper.argumentSelection;

import java.util.Map;

import javax.swing.JLabel;

import commandGenerator.arguments.objects.Achievement;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.gui.helper.components.combobox.CComboBox;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class AchievementSelectionPanel extends HelperPanel implements IBox
{

	private CComboBox achievementBox;
	private JLabel image;

	public AchievementSelectionPanel()
	{
		super(CGConstants.PANELID_ACHIEVEMENT, "GUI:achievement.title");
	}

	@Override
	protected void addComponents()
	{
		addLine(achievementBox, image);
	}

	@Override
	protected void createComponents()
	{
		image = new JLabel(((Achievement) Registry.getObjectFromId("openInventory")).getTexture());
		achievementBox = new CComboBox(CGConstants.PANELID_ACHIEVEMENT, "GUI:achievement.select", Registry.getObjectList(CGConstants.OBJECT_ACHIEVEMENT),
				this);
	}

	@Override
	protected void createListeners()
	{}

	public Achievement getSelectedAchievement()
	{
		DisplayHelper.log("Generated achievement " + achievementBox.getValue().getName());
		return (Achievement) achievementBox.getValue();
	}

	public void setEnabledContent(boolean enabled)
	{
		image.setEnabled(enabled);
		super.setEnabledContent(enabled);
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		if (data.get(CGConstants.DATAID_CHECK) != null && !((boolean) data.get(CGConstants.DATAID_CHECK))) achievementBox.setupFrom(data);
	}

	@Override
	public void updateCombobox()
	{
		image.setIcon(((Achievement) achievementBox.getValue()).getTexture());
	}

	public void setSelected(Achievement achievement)
	{
		this.achievementBox.setSelected(achievement);
	}

}
