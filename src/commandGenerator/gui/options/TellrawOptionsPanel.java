package commandGenerator.gui.options;

import commandGenerator.arguments.objects.EntitySelector;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class TellrawOptionsPanel extends OptionsPanel
{

	private EntitySelectionPanel panelPlayer;
	private ListSelectionPanel panelJson;

	public TellrawOptionsPanel()
	{
		super();

		panelPlayer = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_PLAYERS);
		panelJson = new ListSelectionPanel("GUI:json.text", CGConstants.OBJECT_JSON);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panelPlayer, gbc);
		gbc.gridy++;
		add(panelJson, gbc);
	}

	@Override
	public String generateCommand()
	{

		EntitySelector player = panelPlayer.generateEntity();

		if (player == null) return null;

		TagList list = new TagList() {
			public void askValue()
			{}
		};
		list.setValue(panelJson.getList());

		return "tellraw " + player.commandStructure() + " " + list.commandStructure();
	}

}
