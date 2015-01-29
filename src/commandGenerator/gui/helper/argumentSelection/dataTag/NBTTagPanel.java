package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import commandGenerator.arguments.objects.Item;
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
	private Item item;

	public NBTTagPanel(String title, ObjectBase object, String[][] nbtTags)
	{
		super(CGConstants.PANELID_NBT, title, 780, 160);

		buttonAdd = new CButton("GUI:tag.add");
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				displayer.add();
			}
		});
		buttonRemove = new CButton("GUI:tag.remove");
		buttonRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				displayer.remove();
			}
		});
		buttonHelp = new JButton("?");
		buttonHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				displayer.help();
			}
		});

		Tag[] tags = new Tag[nbtTags.length];
		for (int i = 0; i < tags.length; i++)
			tags[i] = DataTags.init(nbtTags[i]);

		displayer = new TagDisplayer(getPanelId(), tags);
		displayer.updateList(object);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		add(displayer);
		gbc.gridwidth = 1;

		gbc.gridy++;
		add(buttonAdd);
		gbc.gridx++;
		add(buttonRemove);
		gbc.gridx++;
		add(buttonHelp);
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

	public void updateCombobox(ObjectBase object)
	{
		displayer.updateList(object);
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		updateCombobox(item);
	}

	public void setSize(int width, int height)
	{
		super.setPreferredSize(new Dimension(width, height));
		displayer.setSize(width - 50, height - 50);
	}

}
