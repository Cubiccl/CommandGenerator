package commandGenerator.gui.helper.argumentSelection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.components.CButton;
import commandGenerator.gui.helper.components.CComboBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.HelpButton;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.Constants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class TargetSelectionPanel extends HelperPanel
{

	public static final String[] selectors = { "x", "y", "z", "dx", "dy", "dz", "r", "rm", "rx", "rxm", "ry", "rym", "m", "c", "l", "lm", "team", "name",
			"type" };

	private List<String[]> addedSelectors;
	private JComboBox<String> boxEntities, boxSelectors;
	private CButton buttonAdd, buttonRemove;
	private HelpButton buttonHelpEntity, buttonHelpSelector;
	private CEntry entryPlayer;
	private GridBagConstraints gbc = new GridBagConstraints();
	private CLabel labelSelectors, labelEntity, labelSelector;
	private int mode;
	private JScrollPane scrollpane;
	private String[] targets;
	private JEditorPane textarea;

	public TargetSelectionPanel(String id, String title, int mode)
	{
		super(id, title, mode);
	}

	@Override
	protected void addComponents()
	{
		addLine(labelEntity, boxEntities, buttonHelpEntity);
		add(entryPlayer);
		addLine(labelSelector, boxSelectors, buttonHelpSelector);
		addLine(buttonAdd, buttonRemove);
		addLine(labelSelectors, scrollpane);
	}

	private void addSelector()
	{
		String selector = (String) boxSelectors.getSelectedItem();
		String value;
		String title = Lang.get("GUI:selector.add") + " : " + boxSelectors.getSelectedItem();
		String text = Lang.get("HELP:selector." + boxSelectors.getSelectedItem());

		if (selector.equals("x") || selector.equals("y") || selector.equals("z") || selector.equals("dx") || selector.equals("dy") || selector.equals("dz")
				|| selector.equals("r") || selector.equals("rm") || selector.equals("c") || selector.equals("l") || selector.equals("lm")
				|| selector.equals("team") || selector.equals("name"))
		{
			value = (String) JOptionPane.showInputDialog(null, text, title, JOptionPane.OK_CANCEL_OPTION, null, null, "");
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
			JLabel label = new JLabel(text);
			JPanel panel = new JPanel(new GridBagLayout());
			gbc.gridx = 0;
			gbc.gridy = 0;
			panel.add(label);
			gbc.gridy++;
			panel.add(spinner);
			boolean cancel = DisplayHelper.showQuestion(panel, title);
			if (cancel) return;
			value = Integer.toString((int) spinner.getValue());
		} else if (selector.equals("m"))
		{
			JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 3, 1));
			spinner.setPreferredSize(new Dimension(100, 20));
			JLabel label = new JLabel(text);
			JPanel panel = new JPanel(new GridBagLayout());
			gbc.gridx = 0;
			gbc.gridy = 0;
			panel.add(label);
			gbc.gridy++;
			panel.add(spinner);
			boolean cancel = DisplayHelper.showQuestion(panel, title);
			if (cancel) return;
			value = Integer.toString((int) spinner.getValue());

		} else if (selector.equals("type"))
		{

			CComboBox box = new CComboBox(Constants.DATAID_NONE, "GUI:selector.type", Registerer.getObjectList(Constants.OBJECT_ENTITY), null);
			boolean cancel = DisplayHelper.showQuestion(box, title);
			if (cancel) return;
			value = box.getValue().getId();

		} else
		{
			JLabel labelScore = new JLabel(Lang.get("GUI:scoreboard.objective"));
			JLabel labelValue = new JLabel(Lang.get("GUI:scoreboard.score"));
			JLabel label = new JLabel(text);
			JTextField textfieldScore = new JTextField(10), textfieldValue = new JTextField(10);
			JPanel panel = new JPanel(new GridBagLayout());
			gbc.gridwidth = 2;
			gbc.gridheight = 1;
			gbc.gridx = 0;
			gbc.gridy = 0;
			panel.add(label);
			gbc.gridy++;
			gbc.gridwidth = 1;
			panel.add(labelScore);
			gbc.gridy++;
			panel.add(labelValue);
			gbc.gridx++;
			gbc.gridy--;
			panel.add(textfieldScore);
			gbc.gridy++;
			panel.add(textfieldValue);
			panel.setPreferredSize(new Dimension(400, 100));
			boolean cancel = DisplayHelper.showQuestion(panel, title);
			if (cancel) return;
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

			addedSelectors.add(new String[] { (String) boxSelectors.getSelectedItem(), value });
		}
		displaySelectors();
	}

	@Override
	protected void createComponents()
	{
		labelEntity = new CLabel("GUI:selector.entity");
		labelSelector = new CLabel("GUI:selector.choose");
		labelSelectors = new CLabel("GUI:selector.list");

		entryPlayer = new CEntry(Constants.DATAID_NONE, "GUI:player.name", "");
		entryPlayer.setEnabledContent(false);

		buttonHelpEntity = new HelpButton(Lang.get("HELP:selector." + targets[0]), targets[0]);
		buttonHelpSelector = new HelpButton(Lang.get("HELP:selector.x"), "x");

		buttonAdd = new CButton("GUI:selector.add");
		buttonRemove = new CButton("GUI:selector.remove");

		boxEntities = new JComboBox<String>(targets);
		boxEntities.setPreferredSize(new Dimension(100, 20));
		boxEntities.setMinimumSize(new Dimension(100, 20));
		boxSelectors = new JComboBox<String>(selectors);
		boxSelectors.setPreferredSize(new Dimension(100, 20));
		boxSelectors.setMinimumSize(new Dimension(100, 20));

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
			public void actionPerformed(ActionEvent arg0)
			{
				addSelector();
			}
		});
		buttonRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				removeSelector();
			}
		});
		boxEntities.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				boolean player = boxEntities.getSelectedItem().equals(Lang.get("GUI:selector.player"));
				entryPlayer.setEnabledContent(player);
				buttonAdd.setEnabled(!player);
				buttonRemove.setEnabled(!player);
				boxSelectors.setEnabled(!player);
				buttonHelpSelector.setEnabled(!player);
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
		boxSelectors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				buttonHelpSelector.setData(Lang.get("HELP:selector." + boxSelectors.getSelectedItem()), (String) boxSelectors.getSelectedItem());
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

		Target sel = new Target(Target.getTypeFromString((String) boxEntities.getSelectedItem()), addedSelectors);
		DisplayHelper.log("Generated entity selector : " + sel.display());
		return sel;
	}

	public Entity getEntity()
	{
		Entity entity = Entity.player;
		for (int i = 0; i < addedSelectors.size(); i++)
		{
			if (addedSelectors.get(i)[0].equals("type")) entity = (Entity) Registerer.getObjectFromId(addedSelectors.get(i)[1]);
		}
		return entity;
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

	@Override
	public void setEnabledContent(boolean enable)
	{
		super.setEnabledContent(enable);
		boxEntities.setEnabled(enable);
		boxSelectors.setEnabled(enable);
		if (enable) scrollpane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		else scrollpane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	}

	@Override
	protected void setupDetails(Object[] details)
	{
		this.mode = (int) details[0];
		addedSelectors = new ArrayList<String[]>();
		switch (mode)
		{
			case Constants.ENTITIES_PLAYERS:
				targets = new String[] { "@a", "@p", "@r", Lang.get("GUI:selector.player") };
				break;
			case Constants.ENTITIES_NPCS:
				targets = new String[] { "@e" };
				break;

			default:
				targets = new String[] { "@a", "@p", "@r", "@e", Lang.get("GUI:selector.player") };
				break;
		}
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		Target entity = (Target) data.get(getPanelId());
		if (entity == null)
		{
			reset();
			return;
		}

		addedSelectors = entity.getSelectors();
		if (addedSelectors == null) addedSelectors = new ArrayList<String[]>();
		displaySelectors();

		if (entity.getType() == Target.PLAYER)
		{
			boxEntities.setSelectedItem(Lang.get("GUI:selector.player"));
			entryPlayer.setTextField(entity.display());
		}

		else boxEntities.setSelectedItem(Target.selectorsTypes[entity.getType()]);
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
			case Constants.ENTITIES_PLAYERS:
				targets = new String[] { "@a", "@p", "@r", Lang.get("GUI:selector.player") };
				break;
			case Constants.ENTITIES_NPCS:
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
}
