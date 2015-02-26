package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.Constants;

@SuppressWarnings("serial")
public class GiveOptionsPanel extends OptionsPanel
{

	private ItemSelectionPanel panelItem;
	private TargetSelectionPanel panelPlayer;

	public GiveOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelPlayer);
		add(panelItem);
	}

	@Override
	protected void createComponents()
	{
		panelPlayer = new TargetSelectionPanel(Constants.PANELID_TARGET, "GENERAL:target.player", Constants.ENTITIES_PLAYERS);
		panelItem = new ItemSelectionPanel(Constants.PANELID_ITEM, "GUI:give.item", Registerer.getList(Constants.LIST_ITEMS), true, false);
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{
		Target player = panelPlayer.generateEntity();
		Item item = panelItem.generateItem();
		int damage = panelItem.getDamage(), amount = panelItem.getCount();

		if (player == null || item == null) return null;

		String text = "give " + player.commandStructure() + " " + item.getCommandId() + " " + Integer.toString(amount) + " " + Integer.toString(damage);
		if (!panelItem.getItemTag().commandStructure().substring(panelItem.getItemTag().getId().length() + 1).equals("{}")) text += " "
				+ panelItem.getItemTag().commandStructure().substring(panelItem.getItemTag().getId().length() + 1);
		return text;
	}

}
