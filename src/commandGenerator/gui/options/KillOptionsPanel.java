package commandGenerator.gui.options;

import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.panel.OptionsPanel;
import commandGenerator.main.CGConstants;

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
		panelEntity = new TargetSelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);
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
