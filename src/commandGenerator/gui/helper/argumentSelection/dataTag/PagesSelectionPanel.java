package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.components.CButton;

@SuppressWarnings("serial")
public class PagesSelectionPanel extends JPanel
{

	private CButton buttonAdd, buttonRemove;
	private JTextArea textarea;
	private JEditorPane editorpane;
	private JScrollPane scrollpane, scrollpanePage;
	private GridBagConstraints gbc;
	private List<String> pages;

	public PagesSelectionPanel()
	{
		super(new GridBagLayout());

		pages = new ArrayList<String>();

		buttonAdd = new CButton("GUI:page.add");
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				pages.add(textarea.getText());
				textarea.setText("");
				displayLores();
			}
		});
		buttonRemove = new CButton("GUI:page.remove");
		buttonRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				TagSelection.askRemoveText(pages);
				displayLores();
			}
		});

		editorpane = new JEditorPane("text/html", "");
		editorpane.setEditable(false);
		editorpane.setPreferredSize(new Dimension(200, 120));
		textarea = new JTextArea();
		textarea.setPreferredSize(new Dimension(200, 120));

		scrollpane = new JScrollPane(editorpane);
		scrollpane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		scrollpanePage = new JScrollPane(textarea);
		scrollpanePage.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));

		gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		add(scrollpanePage, gbc);
		gbc.gridheight = 1;

		gbc.gridx++;
		add(buttonAdd, gbc);
		gbc.gridy++;
		add(buttonRemove, gbc);

		gbc.gridx++;
		gbc.gridy--;
		gbc.gridheight = 2;
		add(scrollpane, gbc);

	}

	private void displayLores()
	{
		String text = "";
		for (int i = 0; i < pages.size(); i++)
		{
			if (i != 0) text += "<br />";
			if (pages.get(i).length() > 20) text += pages.get(i).substring(0, 20) + "...";
			else text += pages.get(i);
		}
		editorpane.setText(text);
	}

	public List<Tag> getPages()
	{
		if (pages.size() == 0) return null;
		List<Tag> tags = new ArrayList<Tag>();
		for (int i = 0; i < pages.size(); i++)
		{
			tags.add(new TagString().setValue(pages.get(i)));
		}
		return tags;
	}

}
