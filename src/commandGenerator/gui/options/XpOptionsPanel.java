package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.argumentSelection.XpSelectionPanel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class XpOptionsPanel extends OptionsPanel
{

	private EntitySelectionPanel panelEntity;
	private XpSelectionPanel panelXp;

	public XpOptionsPanel()
	{
		super();

		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_PLAYERS);
		panelXp = new XpSelectionPanel("GUI:xp.xp");

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panelXp);
		gbc.gridy++;
		add(panelEntity);
	}

	@Override
	public String generateCommand()
	{
		Target player = panelEntity.generateEntity();
		String xp = panelXp.generateXp();

		if (player == null || xp == null) return null;

		return "xp " + xp + " " + player.commandStructure();
	}

}
