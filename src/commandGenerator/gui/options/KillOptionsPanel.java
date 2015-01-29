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

		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);
		add(panelEntity);
	}

	@Override
	public String generateCommand()
	{
		return "kill " + panelEntity.generateEntity().commandStructure();
	}

}
