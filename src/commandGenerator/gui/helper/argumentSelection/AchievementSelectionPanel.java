package commandGenerator.gui.helper.argumentSelection;

import java.util.Map;

import javax.swing.JLabel;

import commandGenerator.arguments.objects.Achievement;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.gui.helper.components.CComboBox;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.IBox;
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
		image = new JLabel(((Achievement) Registerer.getObjectFromId("openInventory")).getTexture());
		achievementBox = new CComboBox(CGConstants.PANELID_ACHIEVEMENT, "GUI:achievement.select", Registerer.getObjectList(CGConstants.OBJECT_ACHIEVEMENT),
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

}
