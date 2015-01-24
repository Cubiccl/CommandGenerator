package commandGenerator.gui.options;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.EntitySelector;
import commandGenerator.arguments.objects.Sound;
import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.argumentSelection.SoundSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class PlaysoundOptionsPanel extends OptionsPanel
{

	private CCheckBox checkboxCoords;
	private EntitySelectionPanel panelPlayer;
	private CoordSelectionPanel panelCoords;
	private SoundSelectionPanel panelSound;

	public PlaysoundOptionsPanel()
	{
		super();

		checkboxCoords = new CCheckBox(CGConstants.DATAID_SOUND_COORDS, "GUI:playsound.usecoords");
		checkboxCoords.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panelCoords.setEnabled(checkboxCoords.isSelected());
				panelCoords.setEnabledContent(checkboxCoords.isSelected());
			}
		});

		panelPlayer = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_PLAYERS);
		panelCoords = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:playsound.coords", true, false);
		panelCoords.setPreferredSize(new Dimension(250, 200));
		panelCoords.setEnabled(false);
		panelCoords.setEnabledContent(false);
		panelSound = new SoundSelectionPanel("GENERAL:options");

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panelPlayer, gbc);
		gbc.gridy++;
		add(panelSound, gbc);
		gbc.gridy--;
		gbc.gridx++;
		add(panelCoords, gbc);
		gbc.gridy++;
		add(checkboxCoords, gbc);
	}

	@Override
	public String generateCommand()
	{
		EntitySelector player = panelPlayer.generateEntity();
		Coordinates coords;
		Sound sound = panelSound.getSelectedSound();
		String[] options = panelSound.getSoundOptions();

		if (checkboxCoords.isSelected()) coords = panelCoords.generateCoord();
		else coords = new Coordinates(0, 0, 0, true);

		if (player == null || coords == null || options == null || sound == null) return null;

		String command = "playsound";
		command += " " + sound.getId();
		command += " " + player.commandStructure();
		command += " " + coords.commandStructure();
		if (!(options[0] == null && options[1] == null && options[2] == null)) command += " " + options[0] + " " + options[1] + " " + options[2];

		return command;
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		panelCoords.setEnabledContent(checkboxCoords.isSelected());
	}

}
