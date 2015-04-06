package commandGenerator.gui.helper.argumentSelection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.ObjectCreator;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.gui.helper.components.button.LoadButton;
import commandGenerator.gui.helper.components.button.SaveButton;
import commandGenerator.gui.helper.components.combobox.CComboBox;
import commandGenerator.gui.helper.components.combobox.ChoiceComboBox;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class TargetSelectionPanel extends HelperPanel implements ISave
{

	public static final String[] selectors = { "x", "y", "z", "dx", "dy", "dz", "r", "rm", "rx", "rxm", "ry", "rym", "m", "c", "l", "lm", "team", "name",
			"type", "score", "score_min" };

	private List<String[]> addedSelectors;
	private JComboBox<String> boxEntities;
	private ChoiceComboBox boxSelectors;
	private CButton buttonAdd, buttonRemove, buttonSave, buttonLoad;
	private HelpButton buttonHelpEntity;
	private CEntry entryPlayer;
	private CLabel labelSelectors, labelEntity, labelSelector;
	private int mode;
	private JScrollPane scrollpane;
	private String[] targets;
	private JEditorPane textarea;

	public TargetSelectionPanel(String title, int mode)
	{
		super(title);
		this.mode = mode;
		addedSelectors = new ArrayList<String[]>();
		switch (mode)
		{
			case CGConstants.ENTITIES_PLAYERS:
				targets = new String[] { "@a", "@p", "@r", Lang.get("GUI:selector.player") };
				break;
			case CGConstants.ENTITIES_NPCS:
				targets = new String[] { "@e" };
				break;

			default:
				targets = new String[] { "@a", "@p", "@r", "@e", Lang.get("GUI:selector.player") };
				break;
		}

		this.initGui();
	}

	@Override
	protected void addComponents()
	{
		addLine(labelEntity, boxEntities, buttonHelpEntity);
		add(entryPlayer);
		addLine(labelSelector, boxSelectors);
		addLine(buttonAdd, buttonRemove);
		addLine(labelSelectors, scrollpane);
		addLine(buttonSave, buttonLoad);
	}

	private void addSelector()
	{
		String selector = (String) boxSelectors.getSelectedValue();
		String value;
		String title = Lang.get("GUI:selector.add") + " : " + boxSelectors.getSelectedValue();
		String text = Lang.get("HELP:selector." + boxSelectors.getSelectedValue());
		JPanel panel = new JPanel(new GridLayout(3, 1));
		JLabel label = new JLabel(text);
		CCheckBox box = new CCheckBox("GUI:selector.not");
		panel.add(label);
		panel.add(box);

		if (selector.equals("x") || selector.equals("y") || selector.equals("z") || selector.equals("dx") || selector.equals("dy") || selector.equals("dz")
				|| selector.equals("r") || selector.equals("rm") || selector.equals("c") || selector.equals("l") || selector.equals("lm")
				|| selector.equals("team") || selector.equals("name"))
		{
			JTextField field = new JTextField(20);
			panel.add(field);
			if (DisplayHelper.showQuestion(panel, title)) return;
			value = field.getText();
			if (value == null) return;
			if (!(selector.equals("team") || selector.equals("name")))
			{
				try
				{
					int i = Integer.parseInt(value);
					if ((selector.equals("dx") || selector.equals("dy") || selector.equals("dz") || selector.equals("r") || selector.equals("rm")
							|| selector.equals("l") || selector.equals("lm"))
							&& i < 0)
					{
						DisplayHelper.warningPositiveInteger();
						return;
					}
				} catch (Exception ex)
				{
					DisplayHelper.warningNumber();
					return;
				}
			} else if (value.contains(" "))
			{
				DisplayHelper.warningName();
				return;
			}
		} else if (selector.equals("rx") || selector.equals("rxm") || selector.equals("ry") || selector.equals("rym"))
		{
			JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, -180, 180, 1));
			spinner.setPreferredSize(new Dimension(100, 20));
			panel.add(spinner);
			if (DisplayHelper.showQuestion(panel, title)) return;
			value = Integer.toString((int) spinner.getValue());
		} else if (selector.equals("m"))
		{
			ChoiceComboBox choicebox = new ChoiceComboBox("gamemode", new String[] { "survival", "creative", "adventure", "spectator" }, false);
			panel.add(choicebox);
			if (DisplayHelper.showQuestion(panel, title)) return;
			value = Integer.toString(choicebox.getSelectedIndex());

		} else if (selector.equals("type"))
		{
			CComboBox combobox = new CComboBox("GUI:selector.type", Registry.getObjectList(ObjectBase.ENTITY), null);
			panel.add(combobox);
			if (DisplayHelper.showQuestion(panel, title)) return;
			value = combobox.getValue().getId();

		} else
		{
			JTextField textfieldScore = new JTextField(10), textfieldValue = new JTextField(10);
			JPanel panelScore = new JPanel(new GridLayout(2, 2));
			panelScore.add(new JLabel(Lang.get("GUI:objective")));
			panelScore.add(textfieldScore);
			panelScore.add(new JLabel(Lang.get("GUI:scoreboard.score")));
			panelScore.add(textfieldValue);
			if (DisplayHelper.showQuestion(panelScore, title)) return;
			boolean valid = true;
			if (textfieldScore.getText().contains(" "))
			{
				DisplayHelper.warningName();
				valid = false;
			}
			try
			{
				if (Integer.parseInt(textfieldValue.getText()) < 0)
				{
					DisplayHelper.warningPositiveInteger();
					valid = false;
				}
			} catch (Exception e)
			{
				DisplayHelper.warningPositiveInteger();
				valid = false;
			}
			if (!valid) return;
			value = "score_" + textfieldScore.getText();
			if (selector.equals("score_min")) value += "_min";
			addedSelectors.add(new String[] { value, textfieldValue.getText() });
		}

		if (!(selector.equals("score") || selector.equals("score_min")))
		{
			if (!selector.equals("team")) for (int i = 0; i < addedSelectors.size(); i++)
				if (addedSelectors.get(i)[0].equals(selector)) addedSelectors.remove(i);

			addedSelectors.add(new String[] { (String) boxSelectors.getSelectedValue(), value, String.valueOf(box.isSelected()) });
		}
		displaySelectors();
	}

	@Override
	protected void createComponents()
	{
		labelEntity = new CLabel("GUI:selector.entity");
		labelSelector = new CLabel("GUI:selector.choose");
		labelSelectors = new CLabel("GUI:selector.list");

		entryPlayer = new CEntry("GUI:player.name", "");
		entryPlayer.setEnabledContent(false);

		buttonHelpEntity = new HelpButton(Lang.get("HELP:selector." + this.targets[0]), this.targets[0]);

		buttonAdd = new CButton("GUI:selector.add");
		buttonRemove = new CButton("GUI:selector.remove");
		buttonSave = new SaveButton(ObjectBase.TARGET, this);
		buttonLoad = new LoadButton(ObjectBase.TARGET, this);

		boxEntities = new JComboBox<String>(this.targets);
		boxEntities.setPreferredSize(new Dimension(100, 20));
		boxEntities.setMinimumSize(new Dimension(100, 20));
		boxSelectors = new ChoiceComboBox("selector", selectors, true);

		textarea = new JEditorPane("text/html", "");
		textarea.setEditable(false);
		scrollpane = new JScrollPane(textarea);
		scrollpane.getVerticalScrollBar().setUnitIncrement(20);
		scrollpane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		scrollpane.setPreferredSize(new Dimension(200, 100));
		scrollpane.setMinimumSize(new Dimension(200, 100));
	}

	@Override
	protected void createListeners()
	{
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				addSelector();
			}
		});
		buttonRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				removeSelector();
			}
		});
		boxEntities.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				boolean player = boxEntities.getSelectedItem().equals(Lang.get("GUI:selector.player"));
				entryPlayer.setEnabledContent(player);
				buttonAdd.setEnabled(!player);
				buttonRemove.setEnabled(!player);
				boxSelectors.setEnabled(!player);
				labelSelectors.setEnabled(!player);
				textarea.setEnabled(!player);
				if (player) scrollpane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				else scrollpane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));

				String entity = (String) boxEntities.getSelectedItem();
				if (!entity.equals("@e") && !entity.equals("@a") && !entity.equals("@p") && !entity.equals("@r")) buttonHelpEntity.setData(
						Lang.get("HELP:selector.player"), (String) boxEntities.getSelectedItem());
				else buttonHelpEntity.setData(Lang.get("HELP:selector." + boxEntities.getSelectedItem()), (String) boxEntities.getSelectedItem());
			}
		});
	}

	private void displaySelectors()
	{

		String display = "";

		for (int i = 0; i < addedSelectors.size(); i++)
		{
			if (i != 0) display += "<br />";
			display += DisplayHelper.displaySelector(addedSelectors.get(i));
		}

		textarea.setText(display);
	}

	public Target generateEntity()
	{
		if (boxEntities.getSelectedItem().equals(Lang.get("GUI:selector.player")))
		{
			if (entryPlayer.getText().equals("") || entryPlayer.getText().contains(" "))
			{
				DisplayHelper.warningName();
				return null;
			}

			DisplayHelper.log("Generated entity selector : " + entryPlayer.getText());
			return new Target(entryPlayer.getText());
		}

		Target sel = new Target(ObjectCreator.getTargetType((String) boxEntities.getSelectedItem()), addedSelectors);
		DisplayHelper.log("Generated entity selector : " + sel.display());
		return sel;
	}

	public Entity getEntity()
	{
		Entity entity = Entity.player;
		for (int i = 0; i < addedSelectors.size(); i++)
		{
			if (addedSelectors.get(i)[0].equals("type")) entity = (Entity) Registry.getObjectFromId(addedSelectors.get(i)[1]);
		}
		return entity;
	}

	@Override
	public Object getObjectToSave()
	{
		return generateEntity();
	}

	@Override
	public void load(Object object)
	{
		this.setupFrom((Target) object);
	}

	private void removeSelector()
	{
		if (addedSelectors.size() == 0)
		{
			DisplayHelper.showWarning("WARNING:remove.selector");
			return;
		}

		String[] options = new String[addedSelectors.size()];
		for (int i = 0; i < addedSelectors.size(); i++)
		{
			options[i] = DisplayHelper.displaySelector(addedSelectors.get(i));
		}

		String selector = (String) JOptionPane.showInputDialog(null, Lang.get("GUI:remove.selector.ask"), Lang.get("GUI:remove.selector.title"),
				JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);
		if (selector == null) return;

		for (int i = 0; i < addedSelectors.size(); i++)
		{
			if (selector.equals(DisplayHelper.displaySelector(addedSelectors.get(i)))) addedSelectors.remove(i);
		}

		displaySelectors();
	}

	public void setupFrom(Target target)
	{
		if (target == null)
		{
			reset();
			return;
		}

		addedSelectors = target.getSelectors();
		if (addedSelectors == null) addedSelectors = new ArrayList<String[]>();
		displaySelectors();

		if (target.getType() == Target.PLAYER)
		{
			boxEntities.setSelectedItem(Lang.get("GUI:selector.player"));
			entryPlayer.setTextField(target.display());
		}

		else boxEntities.setSelectedItem(Target.selectorsTypes[target.getType()]);
	}

	@Override
	public void updateLang()
	{
		labelEntity.updateLang();
		labelSelector.updateLang();
		labelSelectors.updateLang();
		buttonAdd.updateLang();
		buttonRemove.updateLang();

		switch (mode)
		{
			case CGConstants.ENTITIES_PLAYERS:
				targets = new String[] { "@a", "@p", "@r", Lang.get("GUI:selector.player") };
				break;
			case CGConstants.ENTITIES_NPCS:
				targets = new String[] { "@e" };
				break;

			default:
				targets = new String[] { "@a", "@p", "@r", "@e", Lang.get("GUI:selector.player") };
				break;
		}

		int index = boxEntities.getSelectedIndex();
		boxEntities.setModel(new JComboBox<String>(targets).getModel());
		boxEntities.setSelectedIndex(index);

		displaySelectors();
	}

	@Override
	public void reset()
	{
		super.reset();
		this.addedSelectors.clear();
		this.boxEntities.setSelectedIndex(0);
		this.boxSelectors.setSelected(selectors[0]);
		displaySelectors();
	}
}
