package commandGenerator.gui.helper.argumentSelection;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.dataTag.NBTTagPanel;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.button.LoadButton;
import commandGenerator.gui.helper.components.button.SaveButton;
import commandGenerator.gui.helper.components.combobox.CComboBox;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class EntitySelectionPanel extends HelperPanel implements IBox, ISave
{

	private CButton buttonSave, buttonLoad;
	private CComboBox combobox;
	private Entity[] entityList;
	private JLabel labelName, labelImage;
	private NBTTagPanel panelData;

	public EntitySelectionPanel(String title, ObjectBase[] entityList)
	{
		super(title, entityList, null);
	}

	@Override
	protected void addComponents()
	{
		add(combobox);
		add(labelImage);
		add(labelName);
		add(panelData);
		addLine(buttonSave, buttonLoad);
		updateCombobox();
	}

	@Override
	protected void createComponents()
	{
		labelImage = new JLabel();
		labelImage.setPreferredSize(new Dimension(200, 200));
		labelImage.setMinimumSize(new Dimension(200, 200));
		labelName = new JLabel("", SwingConstants.CENTER);
		labelName.setPreferredSize(new Dimension(200, 20));
		labelName.setMinimumSize(new Dimension(200, 20));

		buttonSave = new SaveButton(CGConstants.OBJECT_ENTITY, this);
		buttonLoad = new LoadButton(CGConstants.OBJECT_ENTITY, this);

		combobox = new CComboBox("GUI:entity.select", entityList, this);

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

	@Override
	public Object getObjectToSave()
	{
		return panelData.getTagList();
	}

	public List<Tag> getTagList()
	{
		return panelData.getTagList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void load(Object object)
	{
		this.setupFrom((Entity) DataTags.getObjectFromTags((List<Tag>) object));
		this.panelData.setupFrom((List<Tag>) object);
	}

	public void setDataTags(List<Tag> list)
	{
		this.panelData.setupFrom(list);
	}

	@Override
	public void setEnabledContent(boolean enable)
	{
		super.setEnabledContent(enable);
		labelImage.setEnabled(enable);
		labelName.setEnabled(enable);
	}

	public void setEntity(Entity entity)
	{
		this.combobox.setSelected(entity);
	}

	public void setSelected(Entity entity)
	{
		combobox.setSelected(entity);
		updateCombobox();
	}

	@Override
	protected void setupDetails(Object[] details)
	{
		ObjectBase[] list = (ObjectBase[]) details[0];

		this.entityList = new Entity[list.length];
		for (int i = 0; i < list.length; i++)
			this.entityList[i] = (Entity) list[i];
	}

	public void setupFrom(Entity entity)
	{
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
		panelData.updateCombobox(combobox.getValue());
		labelImage.setIcon(getEntity().getTexture());
		labelName.setText(getEntity().getName());
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		updateCombobox();
	}

}
