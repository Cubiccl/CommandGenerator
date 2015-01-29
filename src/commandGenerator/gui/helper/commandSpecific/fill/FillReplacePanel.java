package commandGenerator.gui.helper.commandSpecific.fill;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.gui.helper.argumentSelection.BlockSelectionPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class FillReplacePanel extends FillNormalPanel
{

	private BlockSelectionPanel panelBlockReplace;

	public FillReplacePanel()
	{
		super(900);

		panelBlockReplace = new BlockSelectionPanel(CGConstants.PANELID_ITEM, "GUI:fill.blocks_replace", Registerer.getList(CGConstants.LIST_BLOCKS), true);

		remove(buttonHelp);
		remove(comboboxMode);
		add(panelBlockReplace);
	}

	@Override
	public String generateCommand()
	{
		Coordinates coord1 = panelCoord1.generateCoord();
		Coordinates coord2 = panelCoord2.generateCoord();
		Item item = panelBlock.generateBlock();
		Item itemReplace = panelBlockReplace.generateBlock();
		int damage = panelBlock.getDamage();
		int damageReplace = panelBlockReplace.getDamage();

		if (coord1 == null || coord2 == null || item == null) return null;

		return "fill " + coord1.commandStructure() + " " + coord2.commandStructure() + " " + item.getId() + " " + Integer.toString(damage) + " replace "
				+ itemReplace.getId() + " " + Integer.toString(damageReplace);
	}

}
