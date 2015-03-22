package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.objects.Sound;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.gui.helper.components.combobox.CComboBox;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

public class SoundArgument extends Argument implements IBox
{

	private CComboBox box;
	private HelpButton button;

	public SoundArgument(String id, boolean isCompulsery)
	{
		super(id, Argument.SOUND, isCompulsery, 1);
	}

	@Override
	public Component generateComponent()
	{
		JPanel panel = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(this.box);
		gbc.gridx++;
		panel.add(this.button);
		return panel;
	}

	@Override
	public void initGui()
	{
		this.box = new CComboBox(this.getId(), "GUI:" + this.getId(), Registry.getObjectList(CGConstants.OBJECT_SOUND), this);
		this.button = new HelpButton(Lang.get("HELP:sound." + this.box.getValue().getId()), this.box.getValue().getId());
	}

	@Override
	public String generateCommand()
	{
		Sound sound = (Sound) this.box.getValue();
		if (sound == null) return null;
		return sound.getId();
	}

	@Override
	public void updateCombobox()
	{
		this.button.setData(Lang.get("HELP:sound." + this.box.getValue().getId()), this.box.getValue().getId());
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

}
