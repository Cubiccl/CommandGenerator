package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.GuiHandler;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class PagesSelectionPanel extends CPanel
{

	private CButton buttonAddText, buttonAddJson, buttonEdit, buttonRemove;
	private JEditorPane editorpane;
	private JList<String> list;
	private List<String> pages = new ArrayList<String>();
	private JScrollPane scrollpane, scrolllist;

	public PagesSelectionPanel()
	{
		super("TAGS:pages");
		
		this.initGui();
	}

	@Override
	protected void addComponents()
	{
		addLine(buttonAddJson, buttonAddText);
		addLine(buttonEdit, buttonRemove);
		addLine(scrolllist, scrollpane);
	}

	private void addJson()
	{
		ListSelectionPanel panel = new ListSelectionPanel("GENERAL:text", ObjectBase.JSON);
		if (DisplayHelper.showQuestion(panel, Generator.translate("GENERAL:add_title").replaceAll("<item>", Generator.translate("GENERAL:text")))) return;

		TagList tag = new TagList() {
			@Override
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
		if (DisplayHelper.showQuestion(area, Generator.translate("GENERAL:add_title").replaceAll("<item>", Generator.translate("GENERAL:text")))) return;
		pages.add(area.getText());
		setupList();
	}

	@Override
	protected void createComponents()
	{
		buttonAddText = new CButton("GUI:page.add.text");
		buttonAddText.setDrawType(GuiHandler.FULL - GuiHandler.TOP_RIGHT);
		buttonAddJson = new CButton("GUI:page.add.json");
		buttonAddJson.setDrawType(GuiHandler.FULL - GuiHandler.TOP_LEFT);
		buttonEdit = new CButton("GENERAL:edit_only");
		buttonEdit.setDrawType(GuiHandler.FULL - GuiHandler.BOTTOM_LEFT);
		buttonRemove = new CButton("GENERAL:remove");
		buttonRemove.setDrawType(GuiHandler.FULL - GuiHandler.BOTTOM_RIGHT);

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
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addText();
			}
		});
		buttonAddJson.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addJson();
			}
		});
		buttonEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				edit();
			}
		});
		buttonRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				remove();
			}
		});

		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				display();
			}
		});
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
			if (DisplayHelper.showQuestion(area, Generator.translate("GENERAL:add_title").replaceAll("<item>", Generator.translate("GENERAL:text")))) return;
			pages.set(list.getSelectedIndex(), area.getText());
		}

		setupList();
	}

	private void editJson(String text)
	{
		ListSelectionPanel panel = new ListSelectionPanel("GENERAL:text", ObjectBase.JSON);
		panel.setList(DataTags.generateListFrom(text));
		if (DisplayHelper.showQuestion(panel, Generator.translate("GENERAL:add_title").replaceAll("<item>", Generator.translate("GENERAL:text")))) return;

		TagList tag = new TagList() {
			@Override
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
			names[i] = Generator.translate("GENERAL:text") + " " + (i + 1);
		list.setListData(names);
		display();
	}

}
