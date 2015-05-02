package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.gui.helper.components.panel.TagDisplayer;

@SuppressWarnings("serial")
public class NBTTagPanel extends HelperPanel implements CComponent
{

	private CButton buttonAdd, buttonRemove, buttonHelp;
	private TagDisplayer displayer;
	private Tag[] nbtTags;
	private ObjectBase object;

	public NBTTagPanel(String title, ObjectBase object, String[][] nbtTags)
	{
		super(title);
		this.object = object;
		this.nbtTags = new Tag[nbtTags.length];
		for (int i = 0; i < nbtTags.length; i++)
			this.nbtTags[i] = DataTags.init(nbtTags[i]);
		
		this.initGui();
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
		buttonHelp = new CButton("?", false);
		buttonHelp.setFont(new Font(getName(), Font.PLAIN, 11));
		buttonHelp.setPreferredSize(new Dimension(20, 20));
		buttonHelp.setMinimumSize(new Dimension(20, 20));

		displayer = new TagDisplayer(nbtTags);
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
			@Override
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

	public void setupFrom(List<Tag> list)
	{
		this.displayer.setupFrom(list);
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
