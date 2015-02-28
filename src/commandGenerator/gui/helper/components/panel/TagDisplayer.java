package commandGenerator.gui.helper.components.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.specific.TagBlockEntity;
import commandGenerator.gui.helper.argumentSelection.dataTag.TagSelection;
import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class TagDisplayer extends JPanel implements CComponent
{

	private JEditorPane editorPane;
	private GridBagConstraints gbc = new GridBagConstraints();
	private String id;
	private JList<String> list;
	private ObjectBase object;
	private JScrollPane scrollPane, scrollList;
	private Tag[] tags;
	private List<Tag> values;

	public TagDisplayer(String id, Tag[] tags)
	{
		super(new GridBagLayout());
		this.tags = tags;
		this.id = id;
		values = new ArrayList<Tag>();

		list = new JList<String>(new String[0]);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0)
			{
				display();
			}
		});

		scrollList = new JScrollPane(list);
		scrollList.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		scrollList.setPreferredSize(new Dimension(200, 90));
		scrollList.setMinimumSize(new Dimension(200, 90));

		editorPane = new JEditorPane("text/html", "");
		editorPane.setEditable(false);

		scrollPane = new JScrollPane(editorPane);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		scrollPane.setPreferredSize(new Dimension(400, 90));
		scrollPane.setMinimumSize(new Dimension(400, 90));

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(scrollList, gbc);
		gbc.gridx++;
		add(scrollPane, gbc);

		setPreferredSize(new Dimension(620, 100));
		setMinimumSize(new Dimension(620, 100));
	}

	public void add()
	{
		for (int i = 0; i < values.size(); i++)
		{
			if (values.get(i).getId().equals(getSelected().getId()))
			{
				values.get(i).askValue();
				display();
				return;
			}
		}

		Tag tag = getSelected();
		tag.askValue();
		if (!tag.isEmpty()) values.add(tag);
		display();
	}

	private void display()
	{
		Tag sel = getSelected();
		if (sel == null) return;

		editorPane.setText(Lang.get("GUI:tag.empty"));
		for (int i = 0; i < values.size(); i++)
			if (values.get(i).getId().equals(sel.getId())) editorPane.setText(values.get(i).display(CGConstants.DETAILS_ALL, 0));
	}

	private Tag getSelected()
	{
		String sel = list.getSelectedValue();
		for (int i = 0; i < tags.length; i++)
			if (tags[i].getName().equals(sel)) return tags[i];

		return null;
	}

	public List<Tag> getTagList()
	{
		return values;
	}

	public void help()
	{
		if (tags.length == 0) return;
		Tag tag = getSelected();
		if (tag == null) return;
		DisplayHelper.showHelp(tag.getDescription(), tag.getName());
	}

	public void remove()
	{
		for (int i = 0; i < values.size(); i++)
		{
			if (values.get(i).getId().equals(getSelected().getId()))
			{
				values.remove(i);
				break;
			}
		}
		display();
	}

	@Override
	public void reset()
	{
		values.clear();
		display();
	}

	@Override
	public void setEnabledContent(boolean enable)
	{
		list.setEnabled(enable);
		editorPane.setEnabled(enable);
		scrollList.setEnabled(enable);
		scrollPane.setEnabled(enable);
		if (enable) scrollList.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		else scrollList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		if (enable) scrollPane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		else scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	}

	public void setList(Tag[] nbtTags)
	{
		tags = nbtTags;
		updateList(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setupFrom(Map<String, Object> data)
	{
		if (data.get(id) != null) values = (List<Tag>) data.get(id);
		display();
	}

	@Override
	public void updateLang()
	{
		Tag[] allowedTags = TagSelection.getAllowedTags(tags, object);
		String[] names = new String[allowedTags.length];
		for (int i = 0; i < names.length; i++)
			names[i] = allowedTags[i].getName();
		list.setListData(names);

		if (TagSelection.getAllowedTags(tags, object).length == 0) editorPane.setText(Lang.get("GUI:tag.none"));
		else list.setSelectedIndex(0);
		display();
	}

	public void updateList(ObjectBase object)
	{

		Tag[] allowedTags = TagSelection.getAllowedTags(tags, object);
		this.object = object;

		List<Tag> newTags = new ArrayList<Tag>();

		for (int i = 0; i < values.size(); i++)
		{
			boolean flag = false;
			for (int j = 0; j < allowedTags.length; j++)
			{
				if (values.get(i).getId().equals(allowedTags[j].getId())) flag = true;
			}
			if (flag) newTags.add(values.get(i));
		}

		values = newTags;
		if (allowedTags.length == 0) values.clear();
		updateLang();

		for (int i = 0; i < tags.length; i++)
			if (tags[i] instanceof TagBlockEntity) ((TagBlockEntity) tags[i]).setItem((Item) object);

	}
}
