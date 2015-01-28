package commandGenerator.gui.helper.argumentSelection;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JTextField;

import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Particle;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CComboBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.IBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class ParticleSelectionPanel extends HelperPanel implements IBox
{

	private JTextField textfieldCount;
	private JButton buttonHelp;
	private CCheckBox checkboxCount;
	private CEntry entrySpeed;
	private CComboBox comboboxParticle;
	private BlockSelectionPanel panelBlock;
	private ItemSelectionPanel panelItem;

	public ParticleSelectionPanel(String title)
	{
		super(CGConstants.PANELID_PARTICLE, title, 900, 400);

		buttonHelp = new JButton("?");
		buttonHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				DisplayHelper.showHelp(((Particle) comboboxParticle.getValue()).getDescription(), comboboxParticle.getValue().getName());
			}
		});

		textfieldCount = new JTextField(15);
		textfieldCount.setEnabled(false);

		entrySpeed = new CEntry(CGConstants.DATAID_NONE, "GUI:particle.speed");

		checkboxCount = new CCheckBox(CGConstants.DATAID_NONE, "GUI:particle.count");
		checkboxCount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				textfieldCount.setEnabled(checkboxCount.isSelected());
			}
		});

		comboboxParticle = new CComboBox(CGConstants.PANELID_PARTICLE, "GUI:particle", Registerer.getObjectList(CGConstants.OBJECT_PARTICLE), this);

		panelBlock = new BlockSelectionPanel(CGConstants.PANELID_BLOCK, "GUI:particle.block", Registerer.getList(CGConstants.LIST_BLOCKS), false);
		panelBlock.setVisible(false);
		panelBlock.setPreferredSize(new Dimension(800, 150));
		panelItem = new ItemSelectionPanel(CGConstants.PANELID_ITEM, "GUI:particle.item", Registerer.getList(CGConstants.LIST_ICONS), false, false);
		panelItem.setVisible(false);
		panelItem.setPreferredSize(new Dimension(800, 150));

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(comboboxParticle, gbc);
		gbc.gridx++;
		add(buttonHelp, gbc);

		gbc.gridx--;
		gbc.gridy++;
		gbc.gridwidth = 2;
		add(entrySpeed, gbc);
		gbc.gridwidth = 1;

		gbc.gridy++;
		add(checkboxCount, gbc);
		gbc.gridx++;
		add(textfieldCount, gbc);

		gbc.gridx--;
		gbc.gridy++;
		gbc.gridwidth = 2;
		add(panelBlock, gbc);
		add(panelItem, gbc);
		gbc.gridwidth = 1;

	}

	public Particle generateParticle()
	{
		DisplayHelper.log("Generating particle");
		return (Particle) comboboxParticle.getValue();
	}

	public int getCount()
	{

		if (!checkboxCount.isSelected()) return 0;

		String count = textfieldCount.getText();

		try
		{
			int flag = Integer.parseInt(count);
			if (flag <= 0)
			{
				DisplayHelper.warningPositiveInteger();
				return 0;
			}
		} catch (Exception ex)
		{
			DisplayHelper.warningPositiveInteger();
			return 0;
		}

		return Integer.parseInt(count);
	}

	public int getDamage()
	{
		if (comboboxParticle.getValue().getType() == Particle.BLOCK) return panelBlock.getDamage();
		else return panelItem.getDamage();
	}

	public boolean getIsCount()
	{
		return checkboxCount.isSelected();
	}

	public Item getItem()
	{
		if (comboboxParticle.getValue().getType() == Particle.BLOCK) return panelBlock.generateBlock();
		else return panelItem.generateItem();
	}

	public int getSpeed()
	{
		String speed = entrySpeed.getText();

		try
		{
			int flag = Integer.parseInt(speed);
			if (flag < 0)
			{
				DisplayHelper.warningPositiveInteger();
				return -1;
			}
		} catch (Exception ex)
		{
			DisplayHelper.warningPositiveInteger();
			return -1;
		}

		return Integer.parseInt(speed);
	}

	public String getItemProperties(boolean isItem)
	{
		if (isItem) return getItem().getId() + "_" + getDamage();
		return Integer.toString(getItem().getIdNum() | (getDamage() << 12));
	}

	@Override
	public void updateCombobox()
	{
		panelBlock.setVisible(((Particle) comboboxParticle.getValue()).getParticleType() == Particle.BLOCK);
		panelItem.setVisible(((Particle) comboboxParticle.getValue()).getParticleType() == Particle.ITEM);
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		int[] opt = (int[]) data.get(CGConstants.DATAID_VALUE);
		entrySpeed.setTextField(Integer.toString(opt[0]));
		checkboxCount.setSelected(opt[1] != -1);
		textfieldCount.setEnabled(opt[1] != -1);
		if (opt[1] != -1) textfieldCount.setText(Integer.toString(opt[1]));
	}

}
