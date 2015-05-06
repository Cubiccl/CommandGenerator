package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.ObjectCreator;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagBoolean;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.button.LoadButton;
import commandGenerator.gui.helper.components.button.SaveButton;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class TradeSelectionPanel extends CPanel implements ISave
{

	private CButton buttonSave, buttonLoad;
	private CCheckBox checkboxRewardExp, checkboxBuyB;
	private CEntry entryMaxUses, entryUses;
	private ItemSelectionPanel panelBuy, panelBuyB, panelSell;

	public TradeSelectionPanel(String title)
	{
		super(title);

		this.initGui();
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
		addLine(buttonSave, buttonLoad);
	}

	@Override
	protected void createComponents()
	{
		buttonSave = new SaveButton(ObjectBase.TAG_TRADE, this);
		buttonLoad = new LoadButton(ObjectBase.TAG_TRADE, this);

		entryUses = new CEntry("GUI:trade.use", "0");
		entryMaxUses = new CEntry("GUI:trade.use_max", "10");

		checkboxRewardExp = new CCheckBox("GUI:trade.xp");
		checkboxBuyB = new CCheckBox("GUI:trade.buyb.use");

		panelBuy = new ItemSelectionPanel("GUI:trade.buy", Generator.registry.getList(CGConstants.LIST_ITEMS), true, false);
		panelBuyB = new ItemSelectionPanel("GUI:trade.buyb", Generator.registry.getList(CGConstants.LIST_ITEMS), true, false);
		panelBuyB.setEnabledContent(false);
		panelSell = new ItemSelectionPanel("GUI:trade.sell", Generator.registry.getList(CGConstants.LIST_ITEMS), true, false);
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

		String maxUses = entryMaxUses.getText(), uses = entryUses.getText();
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
			@Override
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
	public Object getObjectToSave()
	{
		return generateTrade();
	}

	@Override
	public void load(Object object)
	{
		System.out.println(((TagCompound) object).commandStructure());
		setupFrom((TagCompound) object);
	}

	public void setupFrom(TagCompound trade)
	{
		if (trade == null)
		{
			reset();
			return;
		}

		for (int i = 0; i < trade.size(); i++)
		{
			Tag tag = trade.get(i);
			if (tag.getId().equals("maxUses")) entryMaxUses.setTextField(String.valueOf(((TagInt) tag).getValue()));
			if (tag.getId().equals("uses")) entryUses.setTextField(String.valueOf(((TagInt) tag).getValue()));
			if (tag.getId().equals("rewardExp")) checkboxRewardExp.setSelected(((TagBoolean) tag).getValue());
			if (tag.getId().equals("buy")) panelBuy.setupFrom(ObjectCreator.generateItemStack((TagCompound) tag));
			if (tag.getId().equals("sell")) panelSell.setupFrom(ObjectCreator.generateItemStack((TagCompound) tag));
			if (tag.getId().equals("buyB"))
			{
				panelBuyB.setupFrom(ObjectCreator.generateItemStack((TagCompound) tag));
				panelBuyB.setEnabledContent(true);
				checkboxBuyB.setSelected(true);
			}
		}
	}

}
