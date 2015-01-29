package commandGenerator.gui.options;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class CloneOptionsPanel extends OptionsPanel
{

	private static final String[] modes1 = { "replace", "masked", "filtered" }, modes2 = { "normal", "force", "move" };

	private CLabel labelMode1, labelMode2;
	private JButton buttonHelp1, buttonHelp2;
	private CoordSelectionPanel panelCoord1, panelCoord2, panelDestination;
	private LangComboBox comboboxMode1, comboboxMode2;

	public CloneOptionsPanel()
	{
		super();

		labelMode1 = new CLabel("GUI:clone.mode1");
		labelMode2 = new CLabel("GUI:clone.mode2");

		buttonHelp1 = new JButton("?");
		buttonHelp1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				DisplayHelper.showHelp(Lang.get("HELP:setblock.mode_" + comboboxMode1.getSelectedIndex()), (String) comboboxMode1.getSelectedItem());
			}
		});
		buttonHelp2 = new JButton("?");
		buttonHelp2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				DisplayHelper.showHelp(Lang.get("HELP:clone.mode_" + comboboxMode2.getSelectedIndex()), (String) comboboxMode2.getSelectedItem());
			}
		});

		panelCoord1 = new CoordSelectionPanel(CGConstants.PANELID_COORDS_START, "GUI:clone.start", true, false);
		panelCoord2 = new CoordSelectionPanel(CGConstants.PANELID_COORDS_END, "GUI:clone.end", true, false);
		panelDestination = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:clone.destination", true, false);

		comboboxMode1 = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:setblock.mode", 3);
		comboboxMode1.setPreferredSize(new Dimension(200, 20));
		comboboxMode2 = new LangComboBox(CGConstants.DATAID_MODE2, "RESOURCES:clone.mode", 3);
		comboboxMode2.setPreferredSize(new Dimension(200, 20));

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panelCoord1);
		gbc.gridx++;
		gbc.gridwidth = 3;
		add(panelCoord2);

		gbc.gridx--;
		gbc.gridy++;
		gbc.gridheight = 2;
		gbc.gridwidth = 1;
		add(panelDestination);

		gbc.gridx++;
		gbc.gridheight = 1;
		add(labelMode1);
		gbc.gridx++;
		add(comboboxMode1);
		gbc.gridx++;
		add(buttonHelp1);

		gbc.gridx--;
		gbc.gridx--;
		gbc.gridy++;
		add(labelMode2);
		gbc.gridx++;
		add(comboboxMode2);
		gbc.gridx++;
		add(buttonHelp2);
	}

	@Override
	public String generateCommand()
	{

		Coordinates coord1 = panelCoord1.generateCoord();
		Coordinates coord2 = panelCoord2.generateCoord();
		Coordinates coordDestination = panelDestination.generateCoord();

		if (coord1 == null || coord2 == null || coordDestination == null) return null;

		return "clone " + coord1.commandStructure() + " " + coord2.commandStructure() + " " + coordDestination.commandStructure() + " "
				+ modes1[comboboxMode1.getSelectedIndex()] + " " + modes2[comboboxMode2.getSelectedIndex()];
	}

}
