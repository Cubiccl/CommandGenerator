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

import commandGenerator.arguments.tags.Tag;
import commandGenerator.gui.helper.components.CButton;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class SpawnPotentialsSelectionPanel extends JPanel
{

	private CButton buttonAdd, buttonRemove;
	private JEditorPane editorpane;
	private JScrollPane scrollpane;
	private List<Tag> tags;
	private GridBagConstraints gbc;

	public SpawnPotentialsSelectionPanel()
	{
		super(new GridBagLayout());

		tags = new ArrayList<Tag>();

		buttonAdd = new CButton("GUI:entity.add");
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				SpawnSelectionPanel panel = new SpawnSelectionPanel();
				if (DisplayHelper.showQuestion(panel, "GUI:entity.add")) return;
				tags.add(panel.getTag());
				displaySpawns();
			}
		});
		buttonRemove = new CButton("GUI:entity.remove");
		buttonRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				TagSelection.askRemove(tags);
				displaySpawns();
			}
		});

		editorpane = new JEditorPane("text/html", "");
		editorpane.setEditable(false);
		editorpane.setPreferredSize(new Dimension(200, 120));

		scrollpane = new JScrollPane(editorpane);
		scrollpane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(buttonAdd, gbc);
		gbc.gridy++;
		add(buttonRemove, gbc);

		gbc.gridx++;
		gbc.gridy--;
		gbc.gridheight = 2;
		add(scrollpane, gbc);
		gbc.gridheight = 1;

	}

	private void displaySpawns()
	{
		String text = "";
		for (int i = 0; i < tags.size(); i++)
		{
			if (i != 0) text += "<br />";
			text += "Entity " + Integer.toString(i) + " : " + tags.get(i).display(CGConstants.DETAILS_ALL, 0);
		}
		editorpane.setText(text);
	}

	public List<Tag> getSpawnPotentials()
	{
		return tags;
	}

}
