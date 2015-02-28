package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.BlockSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.components.panel.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class BlockdataOptionsPanel extends OptionsPanel
{

	private BlockSelectionPanel panelBlockdata;
	private CoordSelectionPanel panelCoord;

	public BlockdataOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelCoord);
		add(panelBlockdata);
	}

	@Override
	protected void createComponents()
	{
		panelCoord = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:blockdata.coords", true, false);
		panelBlockdata = new BlockSelectionPanel(CGConstants.PANELID_BLOCK, "GUI:blockdata.block", Registry.getList(CGConstants.LIST_TILEENTITY), true);
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{
		Coordinates coord = panelCoord.generateCoord();
		TagCompound tag = panelBlockdata.getBlockTag();

		if (coord == null || tag == null) return null;

		return "blockdata " + coord.commandStructure() + " " + tag.commandStructure().substring(tag.getId().length() + 1);
	}

}
