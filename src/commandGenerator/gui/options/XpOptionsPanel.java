package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.XpSelectionPanel;
import commandGenerator.gui.helper.components.panel.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class XpOptionsPanel extends OptionsPanel
{

	private TargetSelectionPanel panelEntity;
	private XpSelectionPanel panelXp;

	public XpOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelXp);
		add(panelEntity);
	}

	@Override
	protected void createComponents()
	{
		panelEntity = new TargetSelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_PLAYERS);
		panelXp = new XpSelectionPanel("GUI:xp.xp");
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{
		Target player = panelEntity.generateEntity();
		String xp = panelXp.generateXp();

		if (player == null || xp == null) return null;

		return "xp " + xp + " " + player.commandStructure();
	}

}
