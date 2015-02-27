package commandGenerator.gui.helper.argumentSelection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.objects.Sound;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CComboBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelpButton;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.IBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class SoundSelectionPanel extends HelperPanel implements IBox
{

	private HelpButton buttonHelpSound;
	private CCheckBox checkboxOptions;
	private CComboBox comboboxSound;
	private CEntry entryVolume, entryPitch, entryVolumeMin;

	public SoundSelectionPanel(String title)
	{
		super(CGConstants.PANELID_SOUND, title);
	}

	@Override
	protected void addComponents()
	{
		addLine(comboboxSound, buttonHelpSound);
		add(checkboxOptions);
		add(entryVolume);
		add(entryPitch);
		add(entryVolumeMin);
	}

	@Override
	protected void createComponents()
	{
		entryVolume = new CEntry(CGConstants.DATAID_NONE, "GUI:playsound.volume", "1");
		entryVolume.setEnabledContent(false);
		entryPitch = new CEntry(CGConstants.DATAID_NONE, "GUI:playsound.pitch", "1");
		entryPitch.setEnabledContent(false);
		entryVolumeMin = new CEntry(CGConstants.DATAID_NONE, "GUI:playsound.volume_min", "1");
		entryVolumeMin.setEnabledContent(false);

		ObjectBase[] list = Registry.getObjectList(CGConstants.OBJECT_SOUND);
		buttonHelpSound = new HelpButton(Lang.get("HELP:sound." + list[0].getId()), list[0].getId());

		checkboxOptions = new CCheckBox(CGConstants.DATAID_NONE, "GUI:playsound.advanced");

		comboboxSound = new CComboBox(CGConstants.DATAID_NONE, "GUI:playsound.choose", list, this);
	}

	@Override
	protected void createListeners()
	{
		checkboxOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				entryVolume.setEnabledContent(checkboxOptions.isSelected());
				entryPitch.setEnabledContent(checkboxOptions.isSelected());
				entryVolumeMin.setEnabledContent(checkboxOptions.isSelected());
			}
		});
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
	public void updateCombobox()
	{
		buttonHelpSound.setData(Lang.get("HELP:sound." + comboboxSound.getValue().getId()), comboboxSound.getValue().getName());
	}

}
