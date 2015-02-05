package commandGenerator.gui.options;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.PanelCommandSelection;
import commandGenerator.gui.helper.argumentSelection.BlockSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class ExecuteOptionsPanel extends OptionsPanel
{

	private CCheckBox checkboxDetect;
	private BlockSelectionPanel panelBlock;
	private PanelCommandSelection panelCommand;
	private CoordSelectionPanel panelCoord, panelCoordDetect;
	private TargetSelectionPanel panelEntity;
	private String storedCommand;

	public ExecuteOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelEntity);
		add(panelCoord);
		add(panelCoordDetect);
		add(checkboxDetect);
		add(panelBlock);
	}

	@Override
	protected void createComponents()
	{
		checkboxDetect = new CCheckBox(CGConstants.DATAID_CHECK, "GUI:execute.detect");

		panelCoord = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:execute.coords", true, false);
		panelCoordDetect = new CoordSelectionPanel(CGConstants.PANELID_COORDS_START, "GUI:execute.block_coords", true, false);
		panelCoordDetect.setEnabledContent(false);

		panelEntity = new TargetSelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);

		panelBlock = new BlockSelectionPanel(CGConstants.PANELID_BLOCK, "GUI:execute.block", Registerer.getList(CGConstants.LIST_BLOCKS), false);
		panelBlock.setEnabledContent(false);
	}

	@Override
	protected void createListeners()
	{
		checkboxDetect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panelCoordDetect.setEnabledContent(checkboxDetect.isSelected());
				panelBlock.setEnabledContent(checkboxDetect.isSelected());
			}
		});
	}

	@Override
	public String generateCommand()
	{

		Target entity = panelEntity.generateEntity();
		Coordinates coord = panelCoord.generateCoord();

		if (entity == null || coord == null) return null;

		String command = "execute " + entity.commandStructure() + " " + coord.commandStructure() + " ";

		if (checkboxDetect.isSelected())
		{
			Coordinates coordDetect = panelCoordDetect.generateCoord();
			Item item = panelBlock.generateBlock();
			int damage = panelBlock.getDamage();

			if (coordDetect == null || item == null) return null;
			command += "detect " + coordDetect.commandStructure() + " " + item.getId() + " " + Integer.toString(damage) + " ";
		}

		panelCommand = new PanelCommandSelection(false);
		panelCommand.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), Lang.get("GUI:execute.command")));
		panelCommand.setPreferredSize(new Dimension(1100, 650));
		panelCommand.setSelectedCommand(Registerer.getCommandFromId(storedCommand.split(" ")[0]));
		panelCommand.panelOptions.setupFrom(Registerer.getCommandFromId(storedCommand.split(" ")[0]).generateSetup(storedCommand));

		JOptionPane.showMessageDialog(null, panelCommand, Lang.get("GUI:execute.select"), JOptionPane.QUESTION_MESSAGE);
		if (panelCommand.generateCommand() == null) return null;
		return command + panelCommand.generateCommand();
	}

	@Override
	protected void setupDetails(Object[] details)
	{
		storedCommand = "achievement give openInventory @a";
	}

	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		storedCommand = (String) data.get(CGConstants.DATAID_VALUE);
		panelCoordDetect.setEnabledContent(checkboxDetect.isSelected());
		panelBlock.setEnabledContent(checkboxDetect.isSelected());
	}
}
