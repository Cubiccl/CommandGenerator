package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.ObjectLists;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.argumentSelection.BlockSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class BlockdataOptionsPanel extends OptionsPanel {
	
	private CoordSelectionPanel panelCoord;
	private BlockSelectionPanel panelBlockdata;
	
	public BlockdataOptionsPanel() {
		super();
		
		panelCoord = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:blockdata.coords", true, false);
		panelBlockdata = new BlockSelectionPanel(CGConstants.PANELID_BLOCK, "GUI:blockdata.block", ObjectLists.get("blocks"), true);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panelCoord, gbc);
		gbc.gridy++;
		add(panelBlockdata, gbc);
	}
	
	@Override
	public String generateCommand() {
		Coordinates coord = panelCoord.generateCoord();
		TagCompound tag = panelBlockdata.getBlockTag();
		
		if (coord == null || tag == null) return null;
		
		return "blockdata "  + coord.commandStructure() + " " + tag.commandStructure().substring(tag.getId().length() + 1);
	}

}
