package commandGenerator.gui.options;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.PanelCommandSelection;
import commandGenerator.gui.helper.argumentSelection.BlockSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.Constants;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class ExecuteOptionsPanel extends OptionsPanel
{

	private CLabel labelExplain;
	private CCheckBox checkboxDetect;
	private BlockSelectionPanel panelBlock;
	private PanelCommandSelection panelCommand;
	private CoordSelectionPanel panelCoord, panelCoordDetect;
	private TargetSelectionPanel panelEntity;
	private String storedCommand;

	public ExecuteOptionsPanel()
	{
		super(-1);
	}

	@Override
	protected void addComponents()
	{
		add(labelExplain);
		add(panelEntity);
		add(panelCoord);
		add(panelCoordDetect);
		add(checkboxDetect);
		add(panelBlock);
	}

	@Override
	protected void createComponents()
	{
		labelExplain = new CLabel("GUI:execute.explain");

		checkboxDetect = new CCheckBox(Constants.DATAID_CHECK, "GUI:execute.detect");

		panelCoord = new CoordSelectionPanel(Constants.PANELID_COORDS, "GUI:execute.coords", true, false);
		panelCoordDetect = new CoordSelectionPanel(Constants.PANELID_COORDS_START, "GUI:execute.block_coords", true, false);

		panelEntity = new TargetSelectionPanel(Constants.PANELID_TARGET, "GENERAL:target.entity", Constants.ENTITIES_ALL);

		panelBlock = new BlockSelectionPanel(Constants.PANELID_BLOCK, "GUI:execute.block", Registerer.getList(Constants.LIST_BLOCKS), false);
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
		panelBlock.setEnabledContent(false);
		panelCoordDetect.setEnabledContent(false);
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
			command += "detect " + coordDetect.commandStructure() + " " + item.getCommandId() + " " + Integer.toString(damage) + " ";
		}

		panelCommand = new PanelCommandSelection(false);
		panelCommand.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), Lang.get("GUI:execute.command")));
		panelCommand.setPreferredSize(new Dimension(1100, 650));
		panelCommand.setSelectedCommand(Registerer.getCommandFromId(storedCommand.split(" ")[0]));
		panelCommand.panelOptions.setupFrom(Registerer.getCommandFromId(storedCommand.split(" ")[0]).generateSetup(storedCommand));

		// Setting the JDialog resizable
		panelCommand.addHierarchyListener(new HierarchyListener() {
			public void hierarchyChanged(HierarchyEvent e)
			{
				Window window = SwingUtilities.getWindowAncestor(panelCommand);
				if (window instanceof Dialog)
				{
					Dialog dialog = (Dialog) window;
					if (!dialog.isResizable())
					{
						dialog.setResizable(true);
					}
				}
			}
		});
		JOptionPane.showMessageDialog(null, panelCommand, Lang.get("GUI:execute.select"), JOptionPane.QUESTION_MESSAGE);
		if (panelCommand.generateCommand() == null) return null;
		storedCommand = panelCommand.generateCommand();
		return command + storedCommand;
	}

	@Override
	protected void setupDetails(Object[] details)
	{
		storedCommand = "achievement give openInventory @a";
	}

	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		storedCommand = (String) data.get(Constants.DATAID_VALUE);
		panelCoordDetect.setEnabledContent(checkboxDetect.isSelected());
		panelBlock.setEnabledContent(checkboxDetect.isSelected());
	}
}
