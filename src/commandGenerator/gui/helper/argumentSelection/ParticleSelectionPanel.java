package commandGenerator.gui.helper.argumentSelection;

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

	private JButton buttonHelp;
	private CCheckBox checkboxCount;
	private CComboBox comboboxParticle;
	private CEntry entrySpeed;
	private BlockSelectionPanel panelBlock;
	private ItemSelectionPanel panelItem;
	private JTextField textfieldCount;

	public ParticleSelectionPanel(String title)
	{
		super(CGConstants.PANELID_PARTICLE, title);
	}

	@Override
	protected void addComponents()
	{
		addLine(comboboxParticle, buttonHelp);
		add(entrySpeed);
		addLine(checkboxCount, textfieldCount);
		addLine(panelBlock, panelItem);
	}

	@Override
	protected void createComponents()
	{
		buttonHelp = new JButton("?");

		textfieldCount = new JTextField(15);
		textfieldCount.setEnabled(false);

		entrySpeed = new CEntry(CGConstants.DATAID_NONE, "GUI:particle.speed");

		checkboxCount = new CCheckBox(CGConstants.DATAID_NONE, "GUI:particle.count");

		comboboxParticle = new CComboBox(CGConstants.PANELID_PARTICLE, "GUI:particle", Registerer.getObjectList(CGConstants.OBJECT_PARTICLE), this);

		panelBlock = new BlockSelectionPanel(CGConstants.PANELID_BLOCK, "GUI:particle.block", Registerer.getList(CGConstants.LIST_BLOCKS), false);
		panelBlock.setVisible(false);
		panelItem = new ItemSelectionPanel(CGConstants.PANELID_ITEM, "GUI:particle.item", Registerer.getList(CGConstants.LIST_ICONS), false, false);
		panelItem.setVisible(false);
	}

	@Override
	protected void createListeners()
	{
		buttonHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				DisplayHelper.showHelp(((Particle) comboboxParticle.getValue()).getDescription(), comboboxParticle.getValue().getName());
			}
		});
		checkboxCount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				textfieldCount.setEnabled(checkboxCount.isSelected());
			}
		});
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

	public String getItemProperties(boolean isItem)
	{
		if (isItem) return getItem().getId() + "_" + getDamage();
		return Integer.toString(getItem().getIdNum() | (getDamage() << 12));
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

	@Override
	public void updateCombobox()
	{
		panelBlock.setVisible(((Particle) comboboxParticle.getValue()).getParticleType() == Particle.BLOCK);
		panelItem.setVisible(((Particle) comboboxParticle.getValue()).getParticleType() == Particle.ITEM);
	}

}
