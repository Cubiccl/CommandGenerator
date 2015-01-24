package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.components.CButton;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class PagesSelectionPanel extends JPanel
{

	private CButton buttonAddText, buttonAddJson, buttonEdit, buttonRemove;
	private JList<String> list;
	private JEditorPane editorpane;
	private JScrollPane scrollpane, scrolllist;
	private GridBagConstraints gbc = new GridBagConstraints();
	private List<String> pages = new ArrayList<String>();

	public PagesSelectionPanel()
	{
		super(new GridBagLayout());

		buttonAddText = new CButton("GUI:page.add.text");
		buttonAddJson = new CButton("GUI:page.add.json");
		buttonEdit = new CButton("GENERAL:edit_only");
		buttonRemove = new CButton("GENERAL:remove");

		buttonAddText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				addText();
			}
		});
		buttonAddJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				addJson();
			}
		});
		buttonEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				edit();
			}
		});
		buttonRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				remove();
			}
		});

		list = new JList<String>(new String[0]);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e)
			{
				display();
			}
		});
		scrolllist = new JScrollPane(list);
		scrolllist.setPreferredSize(new Dimension(100, 120));

		editorpane = new JEditorPane("text/html", "");
		scrollpane = new JScrollPane(editorpane);
		scrollpane.setPreferredSize(new Dimension(300, 120));

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(buttonAddText, gbc);
		gbc.gridx++;
		add(buttonAddJson, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		add(buttonEdit, gbc);
		gbc.gridy++;
		add(buttonRemove, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 3;
		add(scrolllist, gbc);
		gbc.gridx++;
		add(scrollpane, gbc);

	}

	private void remove()
	{
		if (list.getSelectedIndex() == -1) return;
		pages.remove(list.getSelectedIndex());
		setupList();
	}

	private void edit()
	{
		if (list.getSelectedIndex() == -1) return;
		String text = pages.get(list.getSelectedIndex());

		if (text.startsWith("{") && text.endsWith("}"))
		{
			editJson(text);
		} else
		{
			JTextArea area = new JTextArea(text);
			area.setPreferredSize(new Dimension(400, 400));
			if (DisplayHelper.showQuestion(area, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:text")))) return;
			pages.set(list.getSelectedIndex(), area.getText());
		}

		setupList();
	}

	private void editJson(String text)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(CGConstants.PANELID_JSON, DataTags.generateListFrom(text));
		ListSelectionPanel panel = new ListSelectionPanel("GENERAL:text", CGConstants.OBJECT_JSON);
		panel.setupFrom(data);
		if (DisplayHelper.showQuestion(panel, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:text")))) return;

		TagList tag = new TagList() {
			public void askValue()
			{}
		};
		tag.setValue(panel.getList());
		pages.set(list.getSelectedIndex(), tag.commandStructure());
		setupList();
	}

	private void addJson()
	{
		ListSelectionPanel panel = new ListSelectionPanel("GENERAL:text", CGConstants.OBJECT_JSON);
		if (DisplayHelper.showQuestion(panel, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:text")))) return;

		TagList tag = new TagList() {
			public void askValue()
			{}
		};
		tag.setValue(panel.getList());
		pages.add(tag.commandStructure());
		setupList();
	}

	private void addText()
	{
		JTextArea area = new JTextArea();
		area.setPreferredSize(new Dimension(400, 400));
		if (DisplayHelper.showQuestion(area, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:text")))) return;
		pages.add(area.getText());
		setupList();
	}

	private void setupList()
	{
		String[] names = new String[pages.size()];
		for (int i = 0; i < names.length; i++)
			names[i] = Lang.get("GENERAL:text") + " " + (i + 1);
		list.setListData(names);
		display();
	}

	private void display()
	{
		if (list.getSelectedIndex() != -1) editorpane.setText(pages.get(list.getSelectedIndex()));
	}

	public List<Tag> getPages()
	{
		List<Tag> tags = new ArrayList<Tag>();
		for (int i = 0; i < pages.size(); i++)
		{
			tags.add(new TagString().setValue(pages.get(i).replaceAll("\n", Matcher.quoteReplacement("\\n")).replaceAll("\"", Matcher.quoteReplacement("\\\""))));
		}
		return tags;
	}

	public void setup(List<Tag> value)
	{
		if (value == null) return;
		pages.clear();
		for (int i = 0; i < value.size(); i++)
			pages.add(((TagString) value.get(i)).getValue());
	}

}
