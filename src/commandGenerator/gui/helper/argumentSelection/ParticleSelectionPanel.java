package commandGenerator.gui.helper.argumentSelection;

import java.util.Map;

import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Particle;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.gui.helper.components.combobox.CComboBox;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class ParticleSelectionPanel extends HelperPanel implements IBox
{

	private HelpButton buttonHelp;
	private CComboBox comboboxParticle;
	private BlockSelectionPanel panelBlock;
	private ItemSelectionPanel panelItem;

	public ParticleSelectionPanel()
	{
		super(CGConstants.PANELID_PARTICLE, "GENERAL:particle");
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
		ObjectBase[] list = Registry.getObjectList(CGConstants.OBJECT_PARTICLE);
		buttonHelp = new HelpButton(((Particle) list[0]).getDescription(), list[0].getName());

		comboboxParticle = new CComboBox(this.getPanelId(), "GENERAL:particle", Registry.getObjectList(CGConstants.OBJECT_PARTICLE), this);

		panelBlock = new BlockSelectionPanel(CGConstants.PANELID_BLOCK, "GUI:particle.block", Registry.getList(CGConstants.LIST_BLOCKS), false);
		panelBlock.setVisible(false);
		panelItem = new ItemSelectionPanel(CGConstants.PANELID_ITEM, "GUI:particle.item", Registry.getList(CGConstants.LIST_ICONS), false, false);
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

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
	}

	@Override
	public void updateCombobox()
	{
		panelBlock.setVisible(((Particle) comboboxParticle.getValue()).getParticleType() == Particle.BLOCK);
		panelItem.setVisible(((Particle) comboboxParticle.getValue()).getParticleType() == Particle.ITEM);
		buttonHelp.setData(((Particle) comboboxParticle.getValue()).getDescription(), comboboxParticle.getValue().getName());
	}

	public String generateParticleId()
	{
		Particle particle = (Particle) comboboxParticle.getValue();
		if (particle == null) return null;
		if (particle.getParticleType() == Particle.BLOCK || particle.getParticleType() == Particle.ITEM) return particle.getId() + "_"
				+ this.getItemProperties(particle.getParticleType() == Particle.ITEM);
		return particle.getId();
	}
}
