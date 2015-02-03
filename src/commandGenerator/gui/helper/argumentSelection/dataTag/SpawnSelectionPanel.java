package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
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
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.IBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class SpawnSelectionPanel extends HelperPanel implements IBox
{

	private CComboBox combobox;
	private CLabel labelType, labelWeight;
	private NBTTagPanel panelTag;
	private JTextField textfieldWeight;

	public SpawnSelectionPanel()
	{
		super(CGConstants.PANELID_NONE, "GUI:spawn.entity");
	}

	@Override
	protected void addComponents()
	{
		addLine(labelType, combobox);
		addLine(labelWeight, textfieldWeight);
		add(panelTag);
	}

	@Override
	protected void createComponents()
	{
		labelType = new CLabel("GUI:spawn.entity");
		labelWeight = new CLabel("GUI:spawn.weight");

		textfieldWeight = new JTextField(18);
		textfieldWeight.setText("1");

		combobox = new CComboBox(CGConstants.DATAID_NONE, "GUI:entity.select", Entity.getListNoPlayer(), this);

		panelTag = new NBTTagPanel("GUI:entity.tags", Entity.player, DataTags.entities);
	}

	@Override
	protected void createListeners()
	{}

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

	@Override
	public void updateCombobox()
	{
		panelTag.updateCombobox((Entity) combobox.getValue());
	}

}
