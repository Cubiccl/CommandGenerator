package commandGenerator.gui.helper.argumentSelection;

import java.awt.Dimension;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Particle;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.gui.helper.components.combobox.ObjectComboBox;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class ParticleSelectionPanel extends CPanel implements IBox
{

	private HelpButton buttonHelp;
	private ObjectComboBox comboboxParticle;
	private BlockSelectionPanel panelBlock;
	private ItemSelectionPanel panelItem;

	public ParticleSelectionPanel()
	{
		super("GENERAL:particle");
		
		this.initGui();
	}

	@Override
	protected void addComponents()
	{
		addLine(comboboxParticle, buttonHelp);
		addLine(false, panelBlock, panelItem);
	}

	@Override
	protected void createComponents()
	{
		ObjectBase[] list = Generator.registry.getObjectList(ObjectBase.PARTICLE);
		buttonHelp = new HelpButton();
		buttonHelp.setPreferredSize(new Dimension(20, 40));
		buttonHelp.setData(((Particle) list[0]).getDescription(), list[0].getName());

		comboboxParticle = new ObjectComboBox("GENERAL:particle", Generator.registry.getObjectList(ObjectBase.PARTICLE), this);

		panelBlock = new BlockSelectionPanel("GUI:particle.block", Generator.registry.getList(CGConstants.LIST_BLOCKS), false);
		panelBlock.setVisible(false);
		panelItem = new ItemSelectionPanel("GUI:particle.item", Generator.registry.getList(CGConstants.LIST_ICONS), false, false);
		panelItem.setVisible(false);
	}

	@Override
	protected void createListeners()
	{}

	public Particle generateParticle()
	{
		DisplayHelper.log("Generating particle");
		return (Particle) comboboxParticle.getValue();
	}

	public String generateParticleId()
	{
		Particle particle = (Particle) comboboxParticle.getValue();
		if (particle == null) return null;
		if (particle.getParticleType() == Particle.BLOCK || particle.getParticleType() == Particle.ITEM) return particle.getId() + "_"
				+ this.getItemProperties(particle.getParticleType() == Particle.ITEM);
		return particle.getId();
	}

	public int getDamage()
	{
		if (((Particle) comboboxParticle.getValue()).getParticleType() == Particle.BLOCK) return panelBlock.getDamage();
		else return panelItem.getDamage();
	}

	public Item getItem()
	{
		if (((Particle) comboboxParticle.getValue()).getParticleType() == Particle.BLOCK) return panelBlock.generateBlock();
		else return panelItem.generateItem();
	}

	public String getItemProperties(boolean isItem)
	{
		if (isItem) return getItem().getCommandId() + "_" + getDamage();
		int value = getItem().getIdNum() | (getDamage() << 12);
		return Integer.toString(value);
	}

	public void setSelected(Particle particle)
	{
		this.comboboxParticle.setSelected(particle);
	}

	@Override
	public void updateCombobox()
	{
		panelBlock.setVisible(((Particle) comboboxParticle.getValue()).getParticleType() == Particle.BLOCK);
		panelItem.setVisible(((Particle) comboboxParticle.getValue()).getParticleType() == Particle.ITEM);
		buttonHelp.setData(((Particle) comboboxParticle.getValue()).getDescription(), comboboxParticle.getValue().getName());
	}
}
