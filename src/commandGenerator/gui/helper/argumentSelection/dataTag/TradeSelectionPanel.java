package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.ObjectLists;
import commandGenerator.arguments.tags.TagBoolean;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class TradeSelectionPanel extends HelperPanel
{

	private CLabel labelMaxUses, labelUses;
	private JTextField textfieldMaxUses, textfieldUses;
	private CCheckBox checkboxRewardExp, checkboxBuyB;
	private ItemSelectionPanel panelBuy, panelBuyB, panelSell;

	public TradeSelectionPanel(String title)
	{
		super(CGConstants.DATAID_NONE, title, 1650, 800);

		labelMaxUses = new CLabel("GUI:trade.use_max");
		labelUses = new CLabel("GUI:trade.use");

		textfieldMaxUses = new JTextField(10);
		textfieldUses = new JTextField(10);
		textfieldMaxUses.setText("10");
		textfieldUses.setText("0");

		checkboxRewardExp = new CCheckBox(CGConstants.DATAID_NONE, "GUI:trade.xp");
		checkboxBuyB = new CCheckBox(CGConstants.DATAID_NONE, "GUI:trade.buyb.use");
		checkboxBuyB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panelBuyB.setEnabledContent(checkboxBuyB.isSelected());
			}
		});

		panelBuy = new ItemSelectionPanel(CGConstants.DATAID_NONE, "GUI:trade.buy", ObjectLists.get(CGConstants.LIST_ITEMS), true, false);
		panelBuyB = new ItemSelectionPanel(CGConstants.DATAID_NONE, "GUI:trade.buyb", ObjectLists.get(CGConstants.LIST_ITEMS), true, false);
		panelBuyB.setEnabledContent(false);
		panelSell = new ItemSelectionPanel(CGConstants.DATAID_NONE, "GUI:trade.sell", ObjectLists.get(CGConstants.LIST_ITEMS), true, false);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(labelMaxUses, gbc);
		gbc.gridy++;
		add(labelUses, gbc);
		gbc.gridy++;
		add(checkboxRewardExp, gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		add(textfieldMaxUses, gbc);
		gbc.gridy++;
		add(textfieldUses, gbc);
		gbc.gridy++;
		add(checkboxBuyB, gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		gbc.gridheight = 3;
		add(panelBuy, gbc);
		gbc.gridheight = 1;
		gbc.gridy = 3;
		add(panelBuyB, gbc);
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		add(panelSell, gbc);
		gbc.gridwidth = 1;
	}

	public TagCompound generateTrade()
	{

		String maxUses = textfieldMaxUses.getText(), uses = textfieldUses.getText();
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
	public void updateLang()
	{}

}
