package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class PagesSelectionPanel extends HelperPanel
{

	private CButton buttonAddText, buttonAddJson, buttonEdit, buttonRemove;
	private JEditorPane editorpane;
	private JList<String> list;
	private List<String> pages = new ArrayList<String>();
	private JScrollPane scrollpane, scrolllist;

	public PagesSelectionPanel()
	{
		super(CGConstants.DATAID_NONE, "TAGS:pages");
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
		area.setMinimumSize(new Dimension(400, 400));
		if (DisplayHelper.showQuestion(area, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:text")))) return;
		pages.add(area.getText());
		setupList();
	}

	private void display()
	{
		if (list.getSelectedIndex() != -1) editorpane.setText(pages.get(list.getSelectedIndex()));
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

	public List<Tag> getPages()
	{
		List<Tag> tags = new ArrayList<Tag>();
		for (int i = 0; i < pages.size(); i++)
		{
			tags.add(new TagString().setValue(pages.get(i).replaceAll("\n", Matcher.quoteReplacement("\\n")).replaceAll("\"", Matcher.quoteReplacement("\\\""))));
		}
		return tags;
	}

	private void remove()
	{
		if (list.getSelectedIndex() == -1) return;
		pages.remove(list.getSelectedIndex());
		setupList();
	}

	public void setup(List<Tag> value)
	{
		if (value == null) return;
		pages.clear();
		for (int i = 0; i < value.size(); i++)
			pages.add(((TagString) value.get(i)).getValue());
	}

	private void setupList()
	{
		String[] names = new String[pages.size()];
		for (int i = 0; i < names.length; i++)
			names[i] = Lang.get("GENERAL:text") + " " + (i + 1);
		list.setListData(names);
		display();
	}

	@Override
	protected void addComponents()
	{
		addLine(buttonAddJson, buttonAddText);
		addLine(buttonEdit, buttonRemove);
		addLine(scrolllist, scrollpane);
	}

	@Override
	protected void createComponents()
	{
		buttonAddText = new CButton("GUI:page.add.text");
		buttonAddJson = new CButton("GUI:page.add.json");
		buttonEdit = new CButton("GENERAL:edit_only");
		buttonRemove = new CButton("GENERAL:remove");

		list = new JList<String>(new String[0]);
		scrolllist = new JScrollPane(list);
		scrolllist.setPreferredSize(new Dimension(100, 120));
		scrolllist.setMinimumSize(new Dimension(100, 120));

		editorpane = new JEditorPane("text/html", "");
		scrollpane = new JScrollPane(editorpane);
		scrollpane.setPreferredSize(new Dimension(300, 120));
		scrollpane.setMinimumSize(new Dimension(300, 120));
	}

	@Override
	protected void createListeners()
	{

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

		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e)
			{
				display();
			}
		});
	}

}
