package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.components.CButton;
import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.TagDisplayer;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class NBTTagPanel extends HelperPanel implements CComponent
{

	private CButton buttonAdd, buttonRemove;
	private JButton buttonHelp;
	private TagDisplayer displayer;
	private Tag[] nbtTags;
	private ObjectBase object;

	public NBTTagPanel(String title, ObjectBase object, String[][] nbtTags)
	{
		super(CGConstants.PANELID_NBT, title, object, nbtTags);
	}

	@Override
	protected void addComponents()
	{
		add(displayer);
		addLine(buttonAdd, buttonRemove, buttonHelp);
	}

	@Override
	protected void createComponents()
	{
		buttonAdd = new CButton("GUI:tag.add");
		buttonRemove = new CButton("GUI:tag.remove");
		buttonHelp = new JButton("?");

		displayer = new TagDisplayer(getPanelId(), nbtTags);
	}

	@Override
	protected void createListeners()
	{
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				displayer.add();
			}
		});
		buttonRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				displayer.remove();
			}
		});
		buttonHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				displayer.help();
			}
		});
	}

	public TagCompound getNbtTags(String id)
	{
		TagCompound tag = new TagCompound(id) {
			public void askValue()
			{}
		};

		for (int i = 0; i < getTagList().size(); i++)
			tag.addTag(getTagList().get(i));

		return tag;
	}

	public List<Tag> getTagList()
	{
		return displayer.getTagList();
	}

	public void setEnabledContent(boolean enable)
	{
		setEnabled(enable);
		buttonAdd.setEnabled(enable);
		buttonRemove.setEnabled(enable);
		buttonHelp.setEnabled(enable);
		displayer.setEnabledContent(enable);
	}

	@Override
	protected void setupDetails(Object[] details)
	{
		this.object = (ObjectBase) details[0];
		String[][] list = (String[][]) details[1];
		
		this.nbtTags = new Tag[list.length];
		for (int i = 0; i < list.length; i++)
			this.nbtTags[i] = DataTags.init(list[i]);
	}

	public void updateCombobox(ObjectBase object)
	{
		this.object = object;
		displayer.updateList(object);
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		updateCombobox(object);
	}

}
