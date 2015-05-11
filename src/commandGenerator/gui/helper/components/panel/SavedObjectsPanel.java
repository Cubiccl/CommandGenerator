package commandGenerator.gui.helper.components.panel;

import static commandGenerator.arguments.objects.SavedObjects.types;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import commandGenerator.Generator;
import commandGenerator.arguments.objects.Attribute;
import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Effect;
import commandGenerator.arguments.objects.Enchantment;
import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.SavedObjects;
import commandGenerator.arguments.objects.Target;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.GuiHandler;
import commandGenerator.gui.helper.argumentSelection.BlockSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.EffectSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.EnchantSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.dataTag.AttributeSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.dataTag.TradeSelectionPanel;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class SavedObjectsPanel extends JPanel
{

	private CButton buttonAdd, buttonEdit, buttonRemove;
	private Map<String, Object> displayed;
	private GridBagConstraints gbc = new GridBagConstraints();
	private JList<String> listTypes, listObjects;
	private JEditorPane pane;
	private JScrollPane scrollbarText, scrollbarObjects;

	public SavedObjectsPanel()
	{
		super(new GridLayout(1, 3));
		((GridLayout) this.getLayout()).setHgap(10);
		this.displayed = SavedObjects.getList(SavedObjects.types[0]);

		this.buttonAdd = new CButton("GENERAL:add_only");
		this.buttonAdd.setDrawType(GuiHandler.BOTTOM);
		this.buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				create(false);
			}
		});
		this.buttonEdit = new CButton("GENERAL:edit_only");
		this.buttonEdit.setDrawType(GuiHandler.FULL);
		this.buttonEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				create(true);
			}
		});
		this.buttonRemove = new CButton("GENERAL:remove");
		this.buttonRemove.setDrawType(GuiHandler.TOP);
		this.buttonRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (displayed.size() > 0) SavedObjects.remove(SavedObjects.types[listTypes.getSelectedIndex()], listObjects.getSelectedValue());
				listObjects.setListData(getNames());
				display();
			}
		});

		this.listTypes = new JList<String>(this.translateTypes());
		this.listTypes.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		this.listTypes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.listTypes.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0)
			{
				displayed = SavedObjects.getList(SavedObjects.types[listTypes.getSelectedIndex()]);
				listObjects.setListData(getNames());
				display();
			}
		});

		this.listObjects = new JList<String>(this.getNames());
		this.listObjects.setBorder(BorderFactory.createLineBorder(Color.blue));
		this.listObjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.listObjects.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				display();
			}
		});

		this.pane = new JEditorPane("text/html", "");
		this.pane.setEditable(false);

		this.scrollbarText = new JScrollPane(this.pane);
		this.scrollbarText.getVerticalScrollBar().setUnitIncrement(20);
		this.scrollbarObjects = new JScrollPane(this.listObjects);
		this.scrollbarObjects.getVerticalScrollBar().setUnitIncrement(20);

		this.add(this.listTypes);
		this.add(this.scrollbarObjects);
		this.add(this.scrollbarText);

		JPanel subPanel = new JPanel(new GridBagLayout());
		this.gbc.gridx = 0;
		this.gbc.gridy = 0;
		subPanel.add(this.buttonAdd, this.gbc);
		this.gbc.gridy++;
		subPanel.add(this.buttonEdit, this.gbc);
		this.gbc.gridy++;
		subPanel.add(this.buttonRemove, this.gbc);
		this.add(subPanel);

		this.listTypes.setSelectedIndex(0);
		if (this.displayed.size() > 0) this.listObjects.setSelectedIndex(0);
		if (this.displayed.size() > 0) this.pane.setText(ObjectBase.display(this.displayed.get(this.listObjects.getSelectedValue())));
	}

	@SuppressWarnings("unchecked")
	private void create(boolean editing)
	{
		if (editing && listObjects.getSelectedValue() == null) return;
		String title = Generator.translate("GENERAL:add_title").replaceAll("<item>",
				Generator.translate("GENERAL:" + SavedObjects.typeNames[listTypes.getSelectedIndex()]));
		Object object = null;

		switch (types[listTypes.getSelectedIndex()])
		{
			case ObjectBase.ATTRIBUTE:
				AttributeSelectionPanel panelA = new AttributeSelectionPanel();

				if (editing) panelA.setupFrom((Attribute) displayed.get(listObjects.getSelectedValue()));

				if (DisplayHelper.showQuestion(panelA, title)) return;
				object = panelA.getAttribute();
				break;

			case ObjectBase.EFFECT:
				EffectSelectionPanel panelEf = new EffectSelectionPanel("GENERAL:effect");

				if (editing) panelEf.setupFrom((Effect) displayed.get(listObjects.getSelectedValue()));

				if (DisplayHelper.showQuestion(panelEf, title)) return;
				object = panelEf.generateEffect();
				break;

			case ObjectBase.ENCHANTMENT:
				EnchantSelectionPanel panelEn = new EnchantSelectionPanel("GENERAL:enchant", false);

				if (editing) panelEn.setupFrom((Enchantment) displayed.get(listObjects.getSelectedValue()));

				if (DisplayHelper.showQuestion(panelEn, title)) return;
				object = panelEn.generateEnchantment();
				break;

			case ObjectBase.ITEM:
				ItemSelectionPanel panelI = new ItemSelectionPanel("GENERAL:item", Generator.registry.getList(CGConstants.LIST_ITEMS), true, true);

				if (editing) panelI.setupFrom((ItemStack) displayed.get(listObjects.getSelectedValue()));

				if (DisplayHelper.showQuestion(panelI, title)) return;
				object = panelI.getItemStack();
				break;

			case ObjectBase.BLOCK:
				BlockSelectionPanel panelB = new BlockSelectionPanel("GENERAL:block", Generator.registry.getList(CGConstants.LIST_BLOCKS), true);

				if (editing) panelB.setupFrom((ItemStack) displayed.get(listObjects.getSelectedValue()));

				if (DisplayHelper.showQuestion(panelB, title)) return;
				object = panelB.getItemStack();
				break;

			case ObjectBase.COORD:
				CoordSelectionPanel panelC = new CoordSelectionPanel("GENERAL:coordinate", true, true);

				if (editing) panelC.setupFrom((Coordinates) displayed.get(listObjects.getSelectedValue()));

				if (DisplayHelper.showQuestion(panelC, title)) return;
				object = panelC.generateCoord();
				break;

			case ObjectBase.ENTITY:
				EntitySelectionPanel panelEnt = new EntitySelectionPanel("GENERAL:entity", Generator.registry.getObjectList(ObjectBase.ENTITY));

				if (editing)
				{
					panelEnt.setDataTags((List<Tag>) displayed.get(listObjects.getSelectedValue()));
					panelEnt.setupFrom((Entity) DataTags.getObjectFromTags((List<Tag>) displayed.get(listObjects.getSelectedValue())));
				}
				if (DisplayHelper.showQuestion(panelEnt, title)) return;
				object = panelEnt.getTagList();
				break;

			case ObjectBase.TAG_TRADE:
				TradeSelectionPanel panelTr = new TradeSelectionPanel("GENERAL:trade");
				JScrollPane scrollpane = new JScrollPane(panelTr);
				scrollpane.getVerticalScrollBar().setUnitIncrement(20);
				scrollpane.getHorizontalScrollBar().setUnitIncrement(20);
				scrollpane.setPreferredSize(new Dimension(840, 600));

				if (editing) panelTr.setupFrom(new TagCompound() {
					@Override
					public void askValue()
					{}
				}.setValue((List<Tag>) displayed.get(listObjects.getSelectedValue())));

				if (DisplayHelper.showQuestion(scrollpane, title)) return;
				object = panelTr.generateTrade();
				break;

			case ObjectBase.TARGET:
				TargetSelectionPanel panelTa = new TargetSelectionPanel("GENERAL:target", CGConstants.ENTITIES_ALL);

				if (editing) panelTa.setupFrom((Target) displayed.get(listObjects.getSelectedValue()));

				if (DisplayHelper.showQuestion(panelTa, title)) return;
				object = panelTa.generateEntity();
				break;

			default:
				break;
		}

		if (editing) SavedObjects.add(listObjects.getSelectedValue(), types[listTypes.getSelectedIndex()], object);
		else
		{
			String name = DisplayHelper.askObjectName(types[listTypes.getSelectedIndex()]);
			SavedObjects.add(name, types[listTypes.getSelectedIndex()], object);
		}
		String selection = listObjects.getSelectedValue();
		listObjects.setListData(getNames());
		listObjects.setSelectedValue(selection, true);
		display();
	}

	private void display()
	{
		pane.setText("");
		if (displayed.get(listObjects.getSelectedValue()) != null) pane.setText(ObjectBase.display(displayed.get(listObjects.getSelectedValue())));
	}

	private String[] getNames()
	{
		String[] names = new String[displayed.size()];
		for (int i = 0; i < names.length; i++)
		{
			names[i] = displayed.keySet().toArray(new String[0])[i];
		}
		return names;
	}

	private String[] translateTypes()
	{
		String[] names = new String[SavedObjects.typeNames.length];
		for (int i = 0; i < names.length; i++)
			names[i] = Generator.translate("GENERAL:" + SavedObjects.typeNames[i]);
		return names;
	}

}
