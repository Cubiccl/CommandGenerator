package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.ColorSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.combobox.ChoiceComboBox;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class ExplosionSelectionPanel extends HelperPanel
{
	private static final String[] fireworksTypes = { "small", "large", "star", "creeper", "burst" };

	private CButton buttonColors, buttonFadeColors, buttonRemoveColor;
	private CCheckBox checkboxFlicker, checkboxTrail;
	private List<Integer> colors, colorsFade;
	private ChoiceComboBox comboboxType;
	private JEditorPane editorpane;
	private JScrollPane scrollpane;

	public ExplosionSelectionPanel()
	{
		super("TAGS:Explosion");
		
		this.initGui();
	}

	@Override
	protected void addComponents()
	{
		add(comboboxType);
		add(checkboxFlicker);
		add(checkboxTrail);
		add(scrollpane);
		addLine(buttonColors, buttonFadeColors, buttonRemoveColor);
	}

	@Override
	protected void createComponents()
	{
		colors = new ArrayList<Integer>();
		colorsFade = new ArrayList<Integer>();

		buttonColors = new CButton("GUI:color.add");
		buttonFadeColors = new CButton("GUI:color.add_fade");
		buttonRemoveColor = new CButton("GUI:color.remove");

		checkboxFlicker = new CCheckBox("GUI:fireworks.twinkle");
		checkboxTrail = new CCheckBox("GUI:fireworks.trail");

		comboboxType = new ChoiceComboBox("fireworks", fireworksTypes, false);

		editorpane = new JEditorPane("text/html", "");
		editorpane.setEditable(false);

		scrollpane = new JScrollPane(editorpane);
		scrollpane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		scrollpane.setPreferredSize(new Dimension(200, 120));
		scrollpane.setMinimumSize(new Dimension(200, 120));
	}

	@Override
	protected void createListeners()
	{
		buttonColors.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ColorSelectionPanel panelColor = new ColorSelectionPanel("GUI:color.add");
				if (DisplayHelper.showQuestion(panelColor, "GUI:color.add")) return;
				colors.add(panelColor.getColor());
				displayColors();
			}
		});
		buttonFadeColors.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ColorSelectionPanel panelColor = new ColorSelectionPanel("GUI:color.add_fade");
				if (DisplayHelper.showQuestion(panelColor, "GUI:color.add_fade")) return;
				colorsFade.add(panelColor.getColor());
				displayColors();
			}
		});
		buttonRemoveColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				remove();
			}
		});
	}

	private void displayColors()
	{
		String text = "";
		if (colors.size() > 0) text += "Colors<br />";
		for (int i = 0; i < colors.size(); i++)
		{
			text += colors.get(i) + "<br />";
		}
		if (colorsFade.size() > 0) text += "Fade Colors<br />";
		for (int i = 0; i < colorsFade.size(); i++)
		{
			text += colorsFade.get(i) + "<br />";
		}

		editorpane.setText(text);
	}

	public TagList getColors()
	{
		if (colors.size() == 0) return null;
		List<Tag> tags = new ArrayList<Tag>();
		for (int i = 0; i < colors.size(); i++)
		{
			tags.add(new TagInt().setValue(colors.get(i)));
		}
		TagList tag = new TagList("Colors") {
			@Override
			public void askValue()
			{}
		};
		tag.setValue(tags);
		return tag;
	}

	public TagList getFadeColors()
	{
		if (colorsFade.size() == 0) return null;
		List<Tag> tags = new ArrayList<Tag>();
		for (int i = 0; i < colorsFade.size(); i++)
		{
			tags.add(new TagInt().setValue(colorsFade.get(i)));
		}
		TagList tag = new TagList("FadeColors") {
			@Override
			public void askValue()
			{}
		};
		tag.setValue(tags);
		return tag;
	}

	public int getType()
	{
		return comboboxType.getSelectedIndex();
	}

	public boolean isFlicker()
	{
		return checkboxFlicker.isSelected();
	}

	public boolean isTrail()
	{
		return checkboxTrail.isSelected();
	}

	private void remove()
	{
		String[] colorArray = new String[colors.size() + colorsFade.size()];
		for (int i = 0; i < colors.size(); i++)
			colorArray[i] = Integer.toString(colors.get(i));
		for (int i = 0; i < colorsFade.size(); i++)
			colorArray[colors.size() + i] = Lang.get("GUI:color.fade") + " : " + colorsFade.get(i);

		JPanel panel = new JPanel();
		JComboBox<String> box = new JComboBox<String>(colorArray);
		panel.add(box);
		if (DisplayHelper.showQuestion(panel, Lang.get("GUI:color.remove"))) return;

		int index = box.getSelectedIndex();
		if (index < colors.size()) colors.remove(index);
		else colorsFade.remove(index - colors.size());
		displayColors();

	}

	public void setup(boolean flicker, boolean trail, int type, TagList color, TagList fade)
	{
		checkboxFlicker.setSelected(flicker);
		checkboxTrail.setSelected(trail);
		comboboxType.setSelected(fireworksTypes[type]);
		colors.clear();
		colorsFade.clear();
		if (color != null)
		{
			for (int i = 0; i < color.getValue().size(); i++)
				colors.add(((TagInt) color.getValue().get(i)).getValue());
		}
		if (color != null)
		{
			for (int i = 0; i < fade.getValue().size(); i++)
				colorsFade.add(((TagInt) fade.getValue().get(i)).getValue());
		}
		displayColors();
	}

}
