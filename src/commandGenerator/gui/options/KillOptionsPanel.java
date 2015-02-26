package commandGenerator.gui.options;

import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.Constants;

@SuppressWarnings("serial")
public class KillOptionsPanel extends OptionsPanel
{

	private TargetSelectionPanel panelEntity;

	public KillOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelEntity);
	}

	@Override
	protected void createComponents()
	{
		panelEntity = new TargetSelectionPanel(Constants.PANELID_TARGET, "GENERAL:target.entity", Constants.ENTITIES_ALL);
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{
		return "kill " + panelEntity.generateEntity().commandStructure();
	}

}
