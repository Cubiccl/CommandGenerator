package commandGenerator.gui.options;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class TpOptionsPanel extends OptionsPanel
{

	private static EntitySelectionPanel panelTarget, panelDestinationEntity;
	private static CoordSelectionPanel panelDestinationCoords;
	private static JRadioButton radiobuttonCoords, radiobuttonEntity;
	private static ButtonGroup radiobuttonGroup;

	public TpOptionsPanel()
	{
		super();

		panelTarget = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);
		panelDestinationEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET2, "GUI:tp.destination.entity", CGConstants.ENTITIES_ALL);
		panelDestinationEntity.setEnabled(false);
		panelDestinationEntity.setEnabledContent(false);
		panelDestinationCoords = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:tp.destination.coords", true, true);

		radiobuttonCoords = new JRadioButton(Lang.get("GUI:tp.coords"));
		radiobuttonCoords.setSelected(true);
		radiobuttonCoords.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panelDestinationCoords.setEnabled(true);
				panelDestinationCoords.setEnabledContent(true);
				panelDestinationEntity.setEnabled(false);
				panelDestinationEntity.setEnabledContent(false);
			}
		});
		radiobuttonEntity = new JRadioButton(Lang.get("GUI:tp.entity"));
		radiobuttonEntity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panelDestinationCoords.setEnabled(false);
				panelDestinationCoords.setEnabledContent(false);
				panelDestinationEntity.setEnabled(true);
				panelDestinationEntity.setEnabledContent(true);
			}
		});
		radiobuttonGroup = new ButtonGroup();
		radiobuttonGroup.add(radiobuttonCoords);
		radiobuttonGroup.add(radiobuttonEntity);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(panelTarget);
		gbc.gridy++;
		gbc.gridwidth = 1;
		add(radiobuttonCoords);
		gbc.gridx++;
		add(radiobuttonEntity);
		gbc.gridx--;
		gbc.gridy++;
		add(panelDestinationCoords);
		gbc.gridx++;
		add(panelDestinationEntity);

	}

	@Override
	public String generateCommand()
	{

		Target target = panelTarget.generateEntity();
		if (target == null) return null;

		if (radiobuttonCoords.isSelected())
		{

			Coordinates coords = panelDestinationCoords.generateCoord();
			if (coords == null) return null;

			String command = "tp";
			command += " " + target.commandStructure();
			command += " " + coords.commandStructure();

			return command;

		} else
		{

			Target entity = panelDestinationEntity.generateEntity();
			if (entity == null) return null;

			String command = "tp";
			command += " " + target.commandStructure();
			command += " " + entity.commandStructure();

			return command;

		}
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		radiobuttonCoords.setText(Lang.get("GUI:tp.coords"));
		radiobuttonEntity.setText(Lang.get("GUI:tp.entity"));
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		radiobuttonCoords.setSelected(!(boolean) data.get(CGConstants.DATAID_ENTITY));
		radiobuttonEntity.setSelected((boolean) data.get(CGConstants.DATAID_ENTITY));
		panelDestinationCoords.setEnabledContent(!(boolean) data.get(CGConstants.DATAID_ENTITY));
		panelDestinationEntity.setEnabledContent((boolean) data.get(CGConstants.DATAID_ENTITY));
	}

}
