package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.arguments.tags.specific.TagExplosion;
import commandGenerator.gui.helper.components.CButton;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.NumberSpinner;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class FireworksSelectionPanel extends HelperPanel
{

	private CButton buttonAdd, buttonRemove;
	private NumberSpinner spinnerFlight;
	private JEditorPane editorpaneExplosions;
	private JScrollPane scrollpane;
	private List<Tag> explosions;

	public FireworksSelectionPanel(String title)
	{
		super(CGConstants.DATAID_NONE, title, 700, 200);

		explosions = new ArrayList<Tag>();

		buttonAdd = new CButton("GUI:fireworks.add");
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				TagExplosion explosion = new TagExplosion();
				explosion.askValue();
				if (!explosion.isEmpty()) explosions.add(explosion);
				displayExplosions();
			}
		});
		buttonRemove = new CButton("GUI:fireworks.remove");
		buttonRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				TagSelection.askRemove(explosions);
				displayExplosions();
			}
		});

		spinnerFlight = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:fireworks.flight", -128, 127, null);
		spinnerFlight.setSelected(0);

		editorpaneExplosions = new JEditorPane("text/html", "");
		editorpaneExplosions.setEditable(false);
		editorpaneExplosions.setPreferredSize(new Dimension(200, 120));

		scrollpane = new JScrollPane(editorpaneExplosions);
		scrollpane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(spinnerFlight, gbc);
		gbc.gridwidth = 1;

		gbc.gridy++;
		add(buttonAdd, gbc);
		gbc.gridx++;
		add(buttonRemove, gbc);

		gbc.gridx++;
		gbc.gridy--;
		gbc.gridheight = 2;
		add(scrollpane, gbc);
		gbc.gridheight = 1;
	}

	private void displayExplosions()
	{
		String text = "";

		for (int i = 0; i < explosions.size(); i++)
		{
			if (i != 0) text += "<br />";
			text += "Explosion " + Integer.toString(i + 1) + " : " + explosions.get(i).display(CGConstants.DETAILS_ALL, 0);
		}

		editorpaneExplosions.setText(text);
	}

	public TagList getExplosions()
	{

		TagList tags = new TagList("Explosions") {
			public void askValue()
			{}
		};
		return tags.setValue(explosions);

	}

	public int getFlight()
	{
		return (int) spinnerFlight.getValue();
	}

	@Override
	public void updateLang()
	{
		updateTitle();
		spinnerFlight.updateLang();
		buttonAdd.updateLang();
		buttonRemove.updateLang();
	}

	public void setup(int flight, List<Tag> list)
	{
		spinnerFlight.setSelected(flight);
		if (list != null) explosions = list;
	}

}
