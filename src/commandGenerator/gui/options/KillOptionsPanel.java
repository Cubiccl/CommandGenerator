package commandGenerator.gui.options;

import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class KillOptionsPanel extends OptionsPanel
{

	private EntitySelectionPanel panelEntity;

	public KillOptionsPanel()
	{
		super();
	}

	@Override
	public String generateCommand()
	{
		return "kill " + panelEntity.generateEntity().commandStructure();
	}

	@Override
	protected void createComponents()
	{
		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);
	}

	@Override
	protected void addComponents()
	{
		add(panelEntity);
	}

	@Override
	protected void createListeners()
	{}

}
