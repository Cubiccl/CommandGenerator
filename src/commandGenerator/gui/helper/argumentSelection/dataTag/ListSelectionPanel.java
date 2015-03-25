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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import commandGenerator.arguments.objects.Attribute;
import commandGenerator.arguments.objects.Effect;
import commandGenerator.arguments.objects.Enchantment;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.arguments.tags.specific.TagExplosion;
import commandGenerator.gui.helper.argumentSelection.EffectSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.EnchantSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.json.JsonSelectionPanel;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class ListSelectionPanel extends HelperPanel
{

	private CButton buttonAdd, buttonEdit, buttonRemove;
	private JEditorPane editorpane;
	private JList<String> jlist;
	private Object[] list;
	private List<Object> objects;

	private JScrollPane scrollpane, scrolllist;
	private int type;

	public ListSelectionPanel(String title, int type, Object... list)
	{
		super(title, type, list);
	}

	private void add()
	{
		switch (type)
		{
			case ObjectBase.ATTRIBUTE:
				AttributeSelectionPanel panelA = new AttributeSelectionPanel();
				if (DisplayHelper.showQuestion(panelA, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:attribute")))) return;
				if (panelA.getAttribute() == null) return;
				objects.add(panelA.getAttribute());
				break;

			case ObjectBase.EFFECT:
				EffectSelectionPanel panelEf = new EffectSelectionPanel("GENERAL:effect");
				if (DisplayHelper.showQuestion(panelEf, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:effect")))) return;
				objects.add(panelEf.generateEffect());
				break;

			case ObjectBase.ENCHANTMENT:
				EnchantSelectionPanel panelEn = new EnchantSelectionPanel("GENERAL:enchant", false);
				if (DisplayHelper.showQuestion(panelEn, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:enchant")))) return;
				if (panelEn.generateEnchantment() == null) return;
				objects.add(panelEn.generateEnchantment());
				break;

			case ObjectBase.ENTITY:
				SpawnSelectionPanel panelSp = new SpawnSelectionPanel();
				if (DisplayHelper.showQuestion(panelSp, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:entity")))) return;
				if (panelSp.getTag() == null) return;
				objects.add(panelSp.getTag());
				break;

			case ObjectBase.ITEM:
				Item[] items = new Item[list.length];
				for (int i = 0; i < items.length; i++)
					items[i] = (Item) list[i];
				ItemSelectionPanel panelI = new ItemSelectionPanel("GENERAL:item", items, true, true);
				if (DisplayHelper.showQuestion(panelI, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:item")))) return;
				objects.add(panelI.getItemStack());
				break;

			case ObjectBase.TAG_EXPLOSION:
				TagExplosion explosion = new TagExplosion();
				explosion.askValue();
				objects.add(explosion);
				break;

			case ObjectBase.TAG_TRADE:
				TradeSelectionPanel panelT = new TradeSelectionPanel("GENERAL:trade");
				JScrollPane scrollpaneT = new JScrollPane(panelT);
				scrollpaneT.getVerticalScrollBar().setUnitIncrement(20);
				scrollpaneT.getHorizontalScrollBar().setUnitIncrement(20);
				scrollpaneT.setPreferredSize(new Dimension(840, 600));
				if (DisplayHelper.showQuestion(scrollpaneT, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:trade")))) return;
				if (panelT.generateTrade() == null) return;
				objects.add(panelT.generateTrade());
				break;

			case ObjectBase.JSON:
				JsonSelectionPanel panelJ = new JsonSelectionPanel("GENERAL:text", true);
				JScrollPane scrollpaneJ = new JScrollPane(panelJ);
				scrollpaneJ.getVerticalScrollBar().setUnitIncrement(20);
				scrollpaneJ.getHorizontalScrollBar().setUnitIncrement(20);
				scrollpaneJ.setPreferredSize(new Dimension(840, 600));
				if (DisplayHelper.showQuestion(scrollpaneJ, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:text")))) return;
				if (panelJ.getTag() == null) return;
				objects.add(panelJ.getTag());
				break;

			case ObjectBase.TAG_PATTERN:
				PatternSelectionPanel panelP = new PatternSelectionPanel();
				if (DisplayHelper.showQuestion(panelP, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:pattern")))) return;
				if (panelP.getPattern() == null) return;
				objects.add(panelP.getPattern());
				break;

			case ObjectBase.STRING:
				String[] strings = new String[list.length];
				for (int i = 0; i < strings.length; i++)
					strings[i] = (String) list[i];
				JPanel panelSt = new JPanel();
				JLabel label = new JLabel(Lang.get("GENERAL:add_only"));
				JComboBox<String> box = new JComboBox<String>(strings);

				panelSt.add(label);
				panelSt.add(box);

				if (DisplayHelper.showQuestion(panelSt, Lang.get("GENERAL:add_only"))) return;
				objects.add(new TagString().setValue((String) box.getSelectedItem()));
				break;

			default:
				break;
		}

		setupList();
	}

	@Override
	protected void addComponents()
	{
		add(scrolllist);
		add(scrollpane);
		addLine(buttonAdd, buttonEdit, buttonRemove);
	}

	@Override
	protected void createComponents()
	{
		buttonAdd = new CButton("GENERAL:add_only");
		buttonEdit = new CButton("GENERAL:edit_only");
		buttonRemove = new CButton("GENERAL:remove");

		jlist = new JList<String>(new String[0]);

		scrolllist = new JScrollPane(jlist);
		scrolllist.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		scrolllist.setPreferredSize(new Dimension(100, 120));
		scrolllist.setMinimumSize(new Dimension(100, 120));

		editorpane = new JEditorPane("text/html", "");
		editorpane.setEditable(false);

		scrollpane = new JScrollPane(editorpane);
		scrollpane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		scrollpane.setPreferredSize(new Dimension(300, 120));
		scrollpane.setMinimumSize(new Dimension(300, 120));
	}

	@Override
	protected void createListeners()
	{
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				add();
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
			public void actionPerformed(ActionEvent arg0)
			{
				remove();
			}
		});
		jlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				display();
			}
		});
	}

	private void display()
	{
		if (jlist.getSelectedIndex() != -1) editorpane.setText(ObjectBase.display(objects.get(jlist.getSelectedIndex())));
	}

	private void edit()
	{
		int nbr = jlist.getSelectedIndex();
		if (nbr == -1) return;
		switch (type)
		{
			case ObjectBase.ATTRIBUTE:
				AttributeSelectionPanel panelA = new AttributeSelectionPanel();
				panelA.setupFrom((Attribute) objects.get(nbr));

				if (DisplayHelper.showQuestion(panelA, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:attribute")))) return;
				if (panelA.getAttribute() == null) return;
				objects.set(nbr, panelA.getAttribute());
				break;

			case ObjectBase.EFFECT:
				EffectSelectionPanel panelEf = new EffectSelectionPanel("GENERAL:effect");
				panelEf.setupFrom((Effect) objects.get(nbr));

				if (DisplayHelper.showQuestion(panelEf, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:effect")))) return;
				objects.set(nbr, panelEf.generateEffect());
				break;

			case ObjectBase.ENCHANTMENT:
				EnchantSelectionPanel panelEn = new EnchantSelectionPanel("GENERAL:enchant", false);
				panelEn.setupFrom((Enchantment) objects.get(nbr));

				if (DisplayHelper.showQuestion(panelEn, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:enchant")))) return;
				if (panelEn.generateEnchantment() == null) return;
				objects.set(nbr, panelEn.generateEnchantment());
				break;

			case ObjectBase.ENTITY:
				SpawnSelectionPanel panelSp = new SpawnSelectionPanel();
				panelSp.setup((TagCompound) objects.get(nbr));

				if (DisplayHelper.showQuestion(panelSp, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:entity")))) return;
				if (panelSp.getTag() == null) return;
				objects.set(nbr, panelSp.getTag());
				break;

			case ObjectBase.ITEM:
				Item[] items = new Item[list.length];
				for (int i = 0; i < items.length; i++)
					items[i] = (Item) list[i];
				ItemSelectionPanel panelI = new ItemSelectionPanel("GENERAL:item", items, true, true);
				panelI.setupFrom((ItemStack) objects.get(nbr));

				if (DisplayHelper.showQuestion(panelI, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:item")))) return;
				objects.set(nbr, panelI.getItemStack());
				break;

			case ObjectBase.TAG_EXPLOSION:
				((TagExplosion) objects.get(nbr)).askValue();
				break;

			case ObjectBase.TAG_TRADE:
				TradeSelectionPanel panelT = new TradeSelectionPanel("GENERAL:trade");
				panelT.setupFrom((TagCompound) objects.get(nbr));

				JScrollPane scrollpaneT = new JScrollPane(panelT);
				scrollpaneT.getVerticalScrollBar().setUnitIncrement(20);
				scrollpaneT.getHorizontalScrollBar().setUnitIncrement(20);
				scrollpaneT.setPreferredSize(new Dimension(840, 600));
				if (DisplayHelper.showQuestion(scrollpaneT, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:trade")))) return;
				if (panelT.generateTrade() == null) return;
				objects.set(nbr, panelT.generateTrade());
				break;

			case ObjectBase.JSON:
				JsonSelectionPanel panelJ = new JsonSelectionPanel("GENERAL:text", true);
				panelJ.setup((TagCompound) objects.get(nbr));
				JScrollPane scrollpaneJ = new JScrollPane(panelJ);
				scrollpaneJ.getVerticalScrollBar().setUnitIncrement(20);
				scrollpaneJ.getHorizontalScrollBar().setUnitIncrement(20);
				scrollpaneJ.setPreferredSize(new Dimension(840, 600));

				if (DisplayHelper.showQuestion(scrollpaneJ, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:text")))) return;
				if (panelJ.getTag() == null) return;
				objects.set(nbr, panelJ.getTag());
				break;

			case ObjectBase.TAG_PATTERN:
				PatternSelectionPanel panelP = new PatternSelectionPanel();
				panelP.setup((TagCompound) objects.get(nbr));
				if (DisplayHelper.showQuestion(panelP, Lang.get("GENERAL:add_title").replaceAll("<item>", Lang.get("GENERAL:pattern")))) return;
				if (panelP.getPattern() == null) return;
				objects.set(nbr, panelP.getPattern());
				break;

			case ObjectBase.STRING:
				String[] strings = new String[list.length];
				for (int i = 0; i < strings.length; i++)
					strings[i] = (String) list[i];
				JPanel panelS = new JPanel();
				JLabel label = new JLabel(Lang.get("GENERAL:edit_only"));
				JComboBox<String> box = new JComboBox<String>(strings);

				int index = 0;
				for (int i = 0; i < strings.length; i++)
					if (strings[i].equals(((TagString) objects.get(nbr)).getValue())) index = i;

				box.setSelectedIndex(index);
				panelS.add(label);
				panelS.add(box);

				if (DisplayHelper.showQuestion(panelS, Lang.get("GENERAL:edit_only"))) return;
				objects.set(nbr, new TagString().setValue((String) box.getSelectedItem()));
				break;

			default:
				break;
		}
		display();
	}

	public List<Tag> getList()
	{
		List<Tag> tags = new ArrayList<Tag>();
		for (int i = 0; i < objects.size(); i++)
			tags.add(ObjectBase.toNBT(objects.get(i)));

		return tags;
	}

	private void remove()
	{
		objects.remove(jlist.getSelectedIndex());
		setupList();
	}

	public void setList(List<Tag> list)
	{
		if (list == null) return;

		objects.clear();
		for (int i = 0; i < list.size(); i++)
			objects.add(ObjectBase.toObject(list.get(i), type));

		setupList();
	}

	@Override
	protected void setupDetails(Object[] details)
	{
		type = (int) details[0];
		list = (Object[]) details[1];
		objects = new ArrayList<Object>();
	}

	private void setupList()
	{
		int index = jlist.getSelectedIndex();
		String name;
		switch (type)
		{
			case ObjectBase.ATTRIBUTE:
				name = Lang.get("GENERAL:attribute");
				break;
			case ObjectBase.EFFECT:
				name = Lang.get("GENERAL:effect");
				break;
			case ObjectBase.ENCHANTMENT:
				name = Lang.get("GENERAL:enchant");
				break;
			case ObjectBase.ENTITY:
				name = Lang.get("GENERAL:entity");
				break;
			case ObjectBase.ITEM:
				name = Lang.get("GENERAL:item");
				break;
			case ObjectBase.TAG_EXPLOSION:
				name = Lang.get("TAGS:Explosion");
				break;
			case ObjectBase.TAG_TRADE:
				name = Lang.get("GENERAL:trade");
				break;
			case ObjectBase.TAG_PATTERN:
				name = Lang.get("GENERAL:pattern");
				break;

			default:
				name = Lang.get("GENERAL:text");
				break;
		}
		String[] ids = new String[objects.size()];
		for (int i = 0; i < ids.length; i++)
			ids[i] = name + " " + (i + 1);

		jlist.setListData(ids);
		if (index == -1) index = 0;
		jlist.setSelectedIndex(index);
		display();
	}

	@Override
	public void updateLang()
	{
		buttonAdd.updateLang();
		buttonRemove.updateLang();
		setupList();
	}

}
