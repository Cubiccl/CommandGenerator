package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagBoolean;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class TradeSelectionPanel extends HelperPanel
{

	private CCheckBox checkboxRewardExp, checkboxBuyB;
	private CEntry entryMaxUses, entryUses;
	private ItemSelectionPanel panelBuy, panelBuyB, panelSell;

	public TradeSelectionPanel(String title)
	{
		super(CGConstants.PANELID_OPTIONS, title);
	}

	@Override
	protected void addComponents()
	{
		add(entryUses);
		add(entryMaxUses);
		add(checkboxRewardExp);
		add(panelBuy);
		add(panelSell);
		add(checkboxBuyB);
		add(panelBuyB);
	}

	@Override
	protected void createComponents()
	{
		entryUses = new CEntry(CGConstants.DATAID_NAME, "GUI:trade.use", "0");
		entryMaxUses = new CEntry(CGConstants.DATAID_NAME2, "GUI:trade.use_max", "10");

		checkboxRewardExp = new CCheckBox(CGConstants.DATAID_MODE, "GUI:trade.xp");
		checkboxBuyB = new CCheckBox(CGConstants.DATAID_MODE2, "GUI:trade.buyb.use");

		panelBuy = new ItemSelectionPanel(CGConstants.PANELID_TARGET, "GUI:trade.buy", Registerer.getList(CGConstants.LIST_ITEMS), true, false);
		panelBuyB = new ItemSelectionPanel(CGConstants.PANELID_TARGET2, "GUI:trade.buyb", Registerer.getList(CGConstants.LIST_ITEMS), true, false);
		panelBuyB.setEnabledContent(false);
		panelSell = new ItemSelectionPanel(CGConstants.PANELID_ITEM, "GUI:trade.sell", Registerer.getList(CGConstants.LIST_ITEMS), true, false);
	}

	@Override
	protected void createListeners()
	{
		checkboxBuyB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panelBuyB.setEnabledContent(checkboxBuyB.isSelected());
			}
		});
	}

	public TagCompound generateTrade()
	{

		String maxUses = entryMaxUses.getText(), uses = entryMaxUses.getText();
		ItemStack buy, buyB, sell;

		try
		{
			int testM = Integer.parseInt(maxUses);
			int testU = Integer.parseInt(uses);
			if (testM < 0 || testU < 0)
			{
				DisplayHelper.warningPositiveInteger();
				return null;
			}
		} catch (Exception ex)
		{
			DisplayHelper.warningPositiveInteger();
			return null;
		}

		buy = new ItemStack(panelBuy.generateItem(), panelBuy.getDamage(), panelBuy.getCount(), panelBuy.getItemTag());
		sell = new ItemStack(panelSell.generateItem(), panelSell.getDamage(), panelSell.getCount(), panelSell.getItemTag());

		TagCompound tag = new TagCompound() {
			public void askValue()
			{}
		};
		tag.addTag(new TagBoolean("rewardExp").setValue(checkboxRewardExp.isSelected()));
		tag.addTag(new TagInt("maxUses").setValue(Integer.parseInt(maxUses)));
		tag.addTag(new TagInt("uses").setValue(Integer.parseInt(uses)));
		tag.addTag(buy.toNBT("buy"));
		if (checkboxBuyB.isSelected())
		{
			buyB = new ItemStack(panelBuyB.generateItem(), panelBuyB.getDamage(), panelBuyB.getCount(), panelBuyB.getItemTag());
			tag.addTag(buyB.toNBT("buyB"));
		}
		tag.addTag(sell.toNBT("sell"));

		return tag;
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		TagCompound trade = (TagCompound) data.get(getPanelId());
		int maxUses = 10, uses = 0;
		boolean reward = false, buyb = false;
		ItemStack buy = new ItemStack((Item) Registerer.getObjectFromId("stone"), 0), buyB = new ItemStack((Item) Registerer.getObjectFromId("stone"), 0), sell = new ItemStack(
				(Item) Registerer.getObjectFromId("stone"), 0);

		for (int i = 0; i < trade.size(); i++)
		{
			Tag tag = trade.get(i);
			if (tag.getId().equals("maxUses")) maxUses = ((TagInt) tag).getValue();
			if (tag.getId().equals("uses")) uses = ((TagInt) tag).getValue();
			if (tag.getId().equals("rewardExp")) reward = ((TagBoolean) tag).getValue();
			if (tag.getId().equals("buy")) buy = ItemStack.generateFrom((TagCompound) tag);
			if (tag.getId().equals("sell")) sell = ItemStack.generateFrom((TagCompound) tag);
			if (tag.getId().equals("buyB"))
			{
				buyB = ItemStack.generateFrom((TagCompound) tag);
				buyb = true;
			}
		}

		Map<String, Object> clean = new HashMap<String, Object>();
		clean.put(CGConstants.DATAID_NAME, Integer.toString(uses));
		clean.put(CGConstants.DATAID_NAME2, Integer.toString(maxUses));
		clean.put(CGConstants.DATAID_MODE, reward);
		clean.put(CGConstants.DATAID_MODE2, buyb);

		clean.put(CGConstants.PANELID_TARGET, buy);
		clean.put(CGConstants.PANELID_TARGET2, buyB);
		clean.put(CGConstants.PANELID_ITEM, sell);

		super.setupFrom(clean);
	}

	@Override
	public void updateLang()
	{}

}
