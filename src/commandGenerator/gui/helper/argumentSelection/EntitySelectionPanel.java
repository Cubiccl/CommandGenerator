package commandGenerator.gui.helper.argumentSelection;

import java.awt.Dimension;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.dataTag.NBTTagPanel;
import commandGenerator.gui.helper.components.CComboBox;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.IBox;
import commandGenerator.main.Constants;

@SuppressWarnings("serial")
public class EntitySelectionPanel extends HelperPanel implements IBox
{

	private Entity[] entityList;
	private CComboBox combobox;
	private JLabel labelName, labelImage;
	private NBTTagPanel panelData;

	public EntitySelectionPanel(String id, String title, ObjectBase[] entityList)
	{
		super(id, title, entityList, null);
	}

	@Override
	protected void addComponents()
	{
		add(combobox);
		add(labelImage);
		add(labelName);
		add(panelData);
		updateCombobox();
	}

	@Override
	protected void createComponents()
	{
		labelImage = new JLabel();
		labelImage.setPreferredSize(new Dimension(200, 200));
		labelImage.setMinimumSize(new Dimension(200, 200));
		labelName = new JLabel("", JLabel.CENTER);
		labelName.setPreferredSize(new Dimension(200, 20));
		labelName.setMinimumSize(new Dimension(200, 20));

		combobox = new CComboBox(Constants.DATAID_NONE, "GUI:entity.select", entityList, this);

		panelData = new NBTTagPanel("GUI:entity.tags", entityList[0], DataTags.entities);
	}

	@Override
	protected void createListeners()
	{}

	public Entity getEntity()
	{
		return (Entity) combobox.getValue();
	}

	public TagCompound getEntityTag()
	{
		return panelData.getNbtTags("Properties");
	}

	public List<Tag> getTagList()
	{
		return panelData.getTagList();
	}

	public void setEnabledContent(boolean enable)
	{
		super.setEnabledContent(enable);
		labelImage.setEnabled(enable);
		labelName.setEnabled(enable);
	}

	@Override
	protected void setupDetails(Object[] details)
	{
		ObjectBase[] list = (ObjectBase[]) details[0];

		this.entityList = new Entity[list.length];
		for (int i = 0; i < list.length; i++)
			this.entityList[i] = (Entity) list[i];
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		Entity entity = (Entity) data.get(getPanelId());
		if (entity == null)
		{
			reset();
			return;
		}

		combobox.setSelected(entity);
	}

	@Override
	public void updateCombobox()
	{
		panelData.updateCombobox((Entity) combobox.getValue());
		labelImage.setIcon(getEntity().getTexture());
		labelName.setText(getEntity().getName());
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		updateCombobox();
	}

	public void setSelected(Entity entity)
	{
		combobox.setSelected(entity);
		updateCombobox();
	}

}
