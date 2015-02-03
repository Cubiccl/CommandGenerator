package commandGenerator.gui.options;

import javax.swing.JLabel;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.dataTag.NBTTagPanel;
import commandGenerator.gui.helper.components.CComboBox;
import commandGenerator.gui.helper.components.IBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class SummonOptionsPanel extends OptionsPanel implements IBox
{

	private CComboBox comboboxEntity;
	private JLabel label;
	private CoordSelectionPanel panelCoord;
	private NBTTagPanel panelTag;

	public SummonOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(comboboxEntity);
		add(label);
		add(panelCoord);
		add(panelTag);
	}

	@Override
	protected void createComponents()
	{
		label = new JLabel(Registerer.getObjectFromId("Item").getTexture());

		comboboxEntity = new CComboBox(CGConstants.DATAID_ENTITY, "GUI:entity.select", Entity.getListNoPlayer(), this);

		panelCoord = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:summon.coords", true, false);
		panelTag = new NBTTagPanel("GUI:entity.tags", Registerer.getObjectFromId("Item"), DataTags.entities);
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{
		Coordinates coord = panelCoord.generateCoord();
		TagCompound tag = panelTag.getNbtTags("tag");
		Entity entity = (Entity) comboboxEntity.getValue();

		if (coord == null) return null;
		if (!tag.commandStructure().substring(tag.getId().length() + 1).equals("{}")) return "summon " + entity.getId() + " " + coord.commandStructure() + " "
				+ tag.commandStructure().substring(tag.getId().length() + 1);
		return "summon " + entity.getId() + " " + coord.commandStructure();
	}

	@Override
	public void updateCombobox()
	{
		panelTag.updateCombobox((Entity) comboboxEntity.getValue());
		label.setIcon(comboboxEntity.getValue().getTexture());
	}

}
