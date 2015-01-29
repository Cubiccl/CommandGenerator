package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.BlockSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class BlockdataOptionsPanel extends OptionsPanel
{

	private CoordSelectionPanel panelCoord;
	private BlockSelectionPanel panelBlockdata;

	public BlockdataOptionsPanel()
	{
		super();
	}

	@Override
	public String generateCommand()
	{
		Coordinates coord = panelCoord.generateCoord();
		TagCompound tag = panelBlockdata.getBlockTag();

		if (coord == null || tag == null) return null;

		return "blockdata " + coord.commandStructure() + " " + tag.commandStructure().substring(tag.getId().length() + 1);
	}

	@Override
	protected void createComponents()
	{
		panelCoord = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:blockdata.coords", true, false);
		panelBlockdata = new BlockSelectionPanel(CGConstants.PANELID_BLOCK, "GUI:blockdata.block", Registerer.getList(CGConstants.LIST_BLOCKS), true);
	}

	@Override
	protected void addComponents()
	{
		add(panelCoord);
		add(panelBlockdata);
	}

	@Override
	protected void createListeners()
	{}

}
