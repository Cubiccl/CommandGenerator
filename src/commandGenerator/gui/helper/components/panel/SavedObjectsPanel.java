package commandGenerator.gui.helper.components.panel;

import static commandGenerator.arguments.objects.SavedObjects.types;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
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

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.objects.SavedObjects;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
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
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class SavedObjectsPanel extends JPanel
{

	private CButton buttonAdd, buttonEdit, buttonRemove;
	private JList<String> listTypes, listObjects;
	private JEditorPane pane;
	private JScrollPane scrollbarText, scrollbarObjects;
	private GridBagConstraints gbc = new GridBagConstraints();
	private Map<String, Object> displayed;

	public SavedObjectsPanel()
	{
		super(new GridLayout(1, 3));
		((GridLayout) getLayout()).setHgap(10);
		displayed = SavedObjects.getList(SavedObjects.types[0]);

		buttonAdd = new CButton("GENERAL:add_only");
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				create(false);
			}
		});
		buttonEdit = new CButton("GENERAL:edit_only");
		buttonEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				create(true);
			}
		});
		buttonRemove = new CButton("GENERAL:remove");
		buttonRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				if (displayed.size() > 0) SavedObjects.remove(SavedObjects.types[listTypes.getSelectedIndex()], listObjects.getSelectedValue());
				listObjects.setListData(getNames());
				display();
			}
		});

		listTypes = new JList<String>(translateTypes());
		listTypes.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		listTypes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTypes.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0)
			{
				displayed = SavedObjects.getList(SavedObjects.types[listTypes.getSelectedIndex()]);
				listObjects.setListData(getNames());
				display();
			}
		});

		listObjects = new JList<String>(getNames());
		listObjects.setBorder(BorderFactory.createLineBorder(Color.blue));
		listObjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listObjects.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e)
			{
				display();
			}
		});

		pane = new JEditorPane("text/html", "");
		pane.setEditable(false);

		scrollbarText = new JScrollPane(pane);
		scrollbarText.getVerticalScrollBar().setUnitIncrement(20);
		scrollbarObjects = new JScrollPane(listObjects);
		scrollbarObjects.getVerticalScrollBar().setUnitIncrement(20);

		add(listTypes);
		add(scrollbarObjects);
		add(scrollbarText);

		JPanel subPanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		subPanel.add(buttonAdd, gbc);
		gbc.gridy++;
		subPanel.add(buttonEdit, gbc);
		gbc.gridy++;
		subPanel.add(buttonRemove, gbc);
		add(subPanel);

		listTypes.setSelectedIndex(0);
		if (displayed.size() > 0) listObjects.setSelectedIndex(0);
		if (displayed.size() > 0) pane.setText(ObjectBase.display(displayed.get(listObjects.getSelectedValue())));
	}

	@SuppressWarnings("unchecked")
	private void create(boolean editing)
	{
		if (editing && listObjects.getSelectedValue() == null) return;
		String title = Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:" + SavedObjects.typeNames[listTypes.getSelectedIndex()]));
		Object object = null;
		Map<String, Object> data = new HashMap<String, Object>();

		switch (types[listTypes.getSelectedIndex()])
		{
			case CGConstants.OBJECT_ATTRIBUTE:
				AttributeSelectionPanel panelA = new AttributeSelectionPanel();

				if (editing)
				{
					data.put(CGConstants.PANELID_OPTIONS, displayed.get(listObjects.getSelectedValue()));
					panelA.setupFrom(data);
				}

				if (DisplayHelper.showQuestion(panelA, title)) return;
				object = panelA.getAttribute();
				break;

			case CGConstants.OBJECT_EFFECT:
				EffectSelectionPanel panelEf = new EffectSelectionPanel(CGConstants.PANELID_EFFECT, "GENERAL:effect");

				if (editing)
				{
					data.put(CGConstants.PANELID_EFFECT, displayed.get(listObjects.getSelectedValue()));
					panelEf.setupFrom(data);
				}

				if (DisplayHelper.showQuestion(panelEf, title)) return;
				object = panelEf.generateEffect();
				break;

			case CGConstants.OBJECT_ENCHANT:
				EnchantSelectionPanel panelEn = new EnchantSelectionPanel(CGConstants.PANELID_ENCHANT, "GENERAL:enchant", false);

				if (editing)
				{
					data.put(CGConstants.PANELID_ENCHANT, displayed.get(listObjects.getSelectedValue()));
					panelEn.setupFrom(data);
				}

				if (DisplayHelper.showQuestion(panelEn, title)) return;
				object = panelEn.generateEnchantment();
				break;

			case CGConstants.OBJECT_ITEM:
				ItemSelectionPanel panelI = new ItemSelectionPanel(CGConstants.PANELID_ITEM, "GENERAL:item", Registry.getList(CGConstants.LIST_ITEMS), true,
						true);

				if (editing)
				{
					data.put(CGConstants.PANELID_ITEM, displayed.get(listObjects.getSelectedValue()));
					panelI.setupFrom(data);
				}

				if (DisplayHelper.showQuestion(panelI, title)) return;
				object = panelI.getItemStack();
				break;

			case CGConstants.OBJECT_BLOCK:
				BlockSelectionPanel panelB = new BlockSelectionPanel(CGConstants.PANELID_BLOCK, "GENERAL:block", Registry.getList(CGConstants.LIST_BLOCKS),
						true);

				if (editing)
				{
					data.put(CGConstants.PANELID_BLOCK, displayed.get(listObjects.getSelectedValue()));
					panelB.setupFrom(data);
				}

				if (DisplayHelper.showQuestion(panelB, title)) return;
				object = panelB.getItemStack();
				break;

			case CGConstants.OBJECT_COORD:
				CoordSelectionPanel panelC = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GENERAL:coordinate", true, true);

				if (editing)
				{
					data.put(CGConstants.PANELID_BLOCK, displayed.get(listObjects.getSelectedValue()));
					panelC.setupFrom(data);
				}

				if (DisplayHelper.showQuestion(panelC, title)) return;
				object = panelC.generateCoord();
				break;

			case CGConstants.OBJECT_ENTITY:
				EntitySelectionPanel panelEnt = new EntitySelectionPanel(CGConstants.PANELID_ENTITY, "GENERAL:entity",
						Registry.getObjectList(CGConstants.OBJECT_ENTITY));

				if (editing)
				{
					data.put(CGConstants.PANELID_NBT, displayed.get(listObjects.getSelectedValue()));
					data.put(CGConstants.PANELID_ENTITY, DataTags.getObjectFromTags((List<Tag>) data.get(CGConstants.PANELID_NBT)));// TODO check this out
					panelEnt.setupFrom(data);
				}

				if (DisplayHelper.showQuestion(panelEnt, title)) return;
				object = panelEnt.getTagList();
				break;

			case CGConstants.OBJECT_TAG_TRADE:
				TradeSelectionPanel panelTr = new TradeSelectionPanel("GENERAL:trade");

				if (editing)
				{
					data.put(CGConstants.PANELID_OPTIONS, displayed.get(listObjects.getSelectedValue()));
					panelTr.setupFrom(data);
				}

				if (DisplayHelper.showQuestion(panelTr, title)) return;
				object = panelTr.generateTrade();
				break;
				
			case CGConstants.OBJECT_TARGET:
				TargetSelectionPanel panelTa = new TargetSelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target", CGConstants.ENTITIES_ALL);

				if (editing)
				{
					data.put(CGConstants.PANELID_TARGET, displayed.get(listObjects.getSelectedValue()));
					panelTa.setupFrom(data);
				}

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
			names[i] = Lang.get("GENERAL:" + SavedObjects.typeNames[i]);
		return names;
	}

}
