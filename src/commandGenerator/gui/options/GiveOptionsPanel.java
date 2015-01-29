package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class GiveOptionsPanel extends OptionsPanel
{

	private EntitySelectionPanel panelPlayer;
	private ItemSelectionPanel panelItem;

	public GiveOptionsPanel()
	{
		super();

		panelPlayer = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_PLAYERS);
		panelItem = new ItemSelectionPanel(CGConstants.PANELID_ITEM, "GUI:give.item", Registerer.getList(CGConstants.LIST_ITEMS), true, false);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panelPlayer);
		gbc.gridy++;
		add(panelItem);
	}

	@Override
	public String generateCommand()
	{
		Target player = panelPlayer.generateEntity();
		Item item = panelItem.generateItem();
		int damage = panelItem.getDamage(), amount = panelItem.getCount();

		if (player == null || item == null) return null;

		String text = "give " + player.commandStructure() + " " + item.getId() + " " + Integer.toString(amount) + " " + Integer.toString(damage);
		if (!panelItem.getItemTag().commandStructure().substring(panelItem.getItemTag().getId().length() + 1).equals("{}")) text += " "
				+ panelItem.getItemTag().commandStructure().substring(panelItem.getItemTag().getId().length() + 1);
		return text;
	}

}
