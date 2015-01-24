package commandGenerator.gui.helper.argumentSelection;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Sound;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CComboBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class SoundSelectionPanel extends HelperPanel
{

	private CEntry entryVolume, entryPitch, entryVolumeMin;
	private JButton buttonHelpSound;
	private CCheckBox checkboxOptions;
	private CComboBox comboboxSound;

	public SoundSelectionPanel(String title)
	{
		super(CGConstants.PANELID_SOUND, title, 500, 200);

		entryVolume = new CEntry(CGConstants.DATAID_NONE, "GUI:playsound.volume");
		entryVolume.setEnabledContent(false);
		entryPitch = new CEntry(CGConstants.DATAID_NONE, "GUI:playsound.pitch");
		entryPitch.setEnabledContent(false);
		entryVolumeMin = new CEntry(CGConstants.DATAID_NONE, "GUI:playsound.volume_min");
		entryVolumeMin.setEnabledContent(false);

		buttonHelpSound = new JButton("?");
		buttonHelpSound.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DisplayHelper.showHelp(Lang.get("HELP:sound." + comboboxSound.getValue().getId()), comboboxSound.getValue().getName());
			}
		});

		checkboxOptions = new CCheckBox(CGConstants.DATAID_NONE, "GUI:playsound.advanced");
		checkboxOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				entryVolume.setEnabledContent(checkboxOptions.isSelected());
				entryPitch.setEnabledContent(checkboxOptions.isSelected());
				entryVolumeMin.setEnabledContent(checkboxOptions.isSelected());
			}
		});

		comboboxSound = new CComboBox(CGConstants.DATAID_NONE, "GUI:playsound.choose", Sound.getList(), null);
		comboboxSound.setPreferredSize(new Dimension(450, 100));

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(comboboxSound, gbc);
		gbc.gridx++;
		add(buttonHelpSound, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		add(checkboxOptions, gbc);
		gbc.gridy++;
		add(entryVolume, gbc);
		gbc.gridy++;
		add(entryPitch, gbc);
		gbc.gridy++;
		add(entryVolumeMin, gbc);
	}

	public Sound getSelectedSound()
	{
		return (Sound) comboboxSound.getValue();
	}

	public String[] getSoundOptions()
	{
		DisplayHelper.log("Generating sound options");
		if (!checkboxOptions.isSelected()) return new String[] { null, null, null };

		String[] options = { entryVolume.getText(), entryPitch.getText(), entryVolumeMin.getText() };

		try
		{
			boolean flag = false;
			int test = Integer.parseInt(options[0]);
			if (test < 0) flag = true;
			test = Integer.parseInt(options[1]);
			if (test < 0) flag = true;
			test = Integer.parseInt(options[2]);
			if (test < 0) flag = true;
			if (flag)
			{
				DisplayHelper.warningPositiveInteger();
				return null;
			}
		} catch (Exception ex)
		{
			DisplayHelper.warningPositiveInteger();
			return null;
		}

		return options;
	}

	@Override
	public void updateLang()
	{
		updateTitle();
		entryPitch.updateLang();
		entryVolume.updateLang();
		entryVolumeMin.updateLang();
		checkboxOptions.updateLang();
		comboboxSound.updateLang();
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		comboboxSound.setSelected((ObjectBase) data.get(getPanelId()));
		checkboxOptions.setSelected((boolean) data.get(CGConstants.DATAID_SOUND_OPTIONS));
		entryVolume.setEnabledContent(checkboxOptions.isSelected());
		entryPitch.setEnabledContent(checkboxOptions.isSelected());
		entryVolumeMin.setEnabledContent(checkboxOptions.isSelected());

		if (data.get(CGConstants.DATAID_SOUND_VOL) != null) entryVolume.setTextField((String) data.get(CGConstants.DATAID_SOUND_VOL));
		if (data.get(CGConstants.DATAID_SOUND_PITCH) != null) entryPitch.setTextField((String) data.get(CGConstants.DATAID_SOUND_PITCH));
		if (data.get(CGConstants.DATAID_SOUND_VOLMIN) != null) entryVolumeMin.setTextField((String) data.get(CGConstants.DATAID_SOUND_VOLMIN));
	}

}
