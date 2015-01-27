package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.components.CComboBox;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.IBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class SpawnSelectionPanel extends JPanel implements IBox
{

	private CLabel labelType, labelWeight;
	private JTextField textfieldWeight;
	private CComboBox combobox;
	private NBTTagPanel panelTag;
	private GridBagConstraints gbc;

	public SpawnSelectionPanel()
	{
		super(new GridBagLayout());

		labelType = new CLabel("GUI:spawn.entity");
		labelWeight = new CLabel("GUI:spawn.weight");

		textfieldWeight = new JTextField(18);
		textfieldWeight.setText("1");

		combobox = new CComboBox(CGConstants.DATAID_NONE, "GUI:entity.select", Entity.getListNoPlayer(), this);

		panelTag = new NBTTagPanel("GUI:entity.tags", Entity.player, DataTags.entities);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(labelType, gbc);
		gbc.gridy++;
		add(labelWeight, gbc);
		gbc.gridy++;
		gbc.gridwidth = 2;
		add(panelTag, gbc);
		gbc.gridwidth = 1;

		gbc.gridx++;
		gbc.gridy = 0;
		add(combobox, gbc);
		gbc.gridy++;
		add(textfieldWeight, gbc);
	}

	public TagCompound getTag()
	{

		String weight = textfieldWeight.getText();
		try
		{
			int test = Integer.parseInt(weight);
			if (test < 1)
			{
				JOptionPane.showMessageDialog(null, Lang.get("WARNING:min").replaceAll("<min>", "1"), Lang.get("WARNING:title"), JOptionPane.WARNING_MESSAGE);
				return null;
			}
		} catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, Lang.get("WARNING:min").replaceAll("<min>", "1"), Lang.get("WARNING:title"), JOptionPane.WARNING_MESSAGE);
			return null;
		}

		TagCompound tag = new TagCompound() {
			public void askValue()
			{}
		};
		tag.addTag(new TagString("Type").setValue(combobox.getValue().getId()));
		tag.addTag(new TagInt("Weight").setValue(Integer.parseInt(weight)));
		tag.addTag(panelTag.getNbtTags("Properties"));
		return tag;
	}

	@Override
	public void updateCombobox()
	{
		panelTag.updateCombobox((Entity) combobox.getValue());
	}

	public void setup(TagCompound nbt)
	{
		for (int i = 0; i < nbt.size(); i++)
		{
			Tag tag = nbt.get(i);
			if (tag.getId().equals("Type")) combobox.setSelected(Registerer.getObjectFromId(((TagString) tag).getValue()));
			if (tag.getId().equals("Weight")) textfieldWeight.setText(Integer.toString(((TagInt) tag).getValue()));
			if (tag.getId().equals("Properties"))
			{
				Map<String, Object> data = new HashMap<String, Object>();
				data.put(CGConstants.PANELID_NBT, ((TagCompound) tag).getValue());
				panelTag.setupFrom(data);
			}
		}
	}

}
