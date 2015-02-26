package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Target;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.Constants;

@SuppressWarnings("serial")
public class TellrawOptionsPanel extends OptionsPanel
{

	private ListSelectionPanel panelJson;
	private TargetSelectionPanel panelPlayer;

	public TellrawOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelPlayer);
		add(panelJson);
	}

	@Override
	protected void createComponents()
	{
		panelPlayer = new TargetSelectionPanel(Constants.PANELID_TARGET, "GENERAL:target.player", Constants.ENTITIES_PLAYERS);
		panelJson = new ListSelectionPanel("GUI:json.text", Constants.OBJECT_JSON);
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{

		Target player = panelPlayer.generateEntity();

		if (player == null) return null;

		TagList list = new TagList() {
			public void askValue()
			{}
		};
		list.setValue(panelJson.getList());

		return "tellraw " + player.commandStructure() + " " + list.commandStructure();
	}

}
