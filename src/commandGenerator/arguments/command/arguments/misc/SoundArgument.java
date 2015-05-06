package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JPanel;

import commandGenerator.Generator;
import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Sound;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.gui.helper.components.combobox.ObjectComboBox;
import commandGenerator.gui.helper.components.icomponent.IBox;

public class SoundArgument extends Argument implements IBox
{

	private ObjectComboBox box;
	private HelpButton button;

	public SoundArgument(String id, boolean isCompulsery)
	{
		super(id, isCompulsery);
	}

	@Override
	public String generateCommand()
	{
		Sound sound = (Sound) this.box.getValue();
		if (sound == null) return null;
		return sound.getId();
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
		this.box = new ObjectComboBox("GUI:" + this.getId(), Generator.registry.getObjectList(ObjectBase.SOUND), this);
		this.button = new HelpButton();
		if (this.box.getValue() != null) this.button.setData(Generator.translate("HELP:sound." + this.box.getValue().getId()), this.box.getValue().getId());
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

	@Override
	public boolean matches(List<String> data)
	{
		return Generator.registry.exists(data.get(0), ObjectBase.SOUND);
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.box.setSelected(Generator.registry.getObjectFromId(data.get(0)));
	}

	@Override
	public void updateCombobox()
	{
		this.button.setData(Generator.translate("HELP:sound." + this.box.getValue().getId()), this.box.getValue().getId());
	}

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof SoundArgument)) return;
		this.box.setSelected(((SoundArgument) toMatch).box.getValue());
	}

	@Override
	public void reset()
	{
		this.box.reset();
	}

}
