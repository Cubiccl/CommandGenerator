package commandGenerator.gui.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Particle;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.argumentSelection.ParticleSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class ParticleOptionsPanel extends OptionsPanel
{

	private CCheckBox checkBoxEntity;
	private CoordSelectionPanel panelCoord1, panelCoord2;
	private EntitySelectionPanel panelEntity;
	private ParticleSelectionPanel panelParticle;

	public ParticleOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelParticle);
		add(panelCoord1);
		add(panelCoord2);
		add(checkBoxEntity);
		add(panelEntity);
	}

	@Override
	protected void createComponents()
	{
		checkBoxEntity = new CCheckBox(CGConstants.DATAID_CHECK, "GUI:particle.target");

		panelParticle = new ParticleSelectionPanel("GUI:particle");
		panelCoord1 = new CoordSelectionPanel(CGConstants.PANELID_COORDS_START, "GUI:particle.start", true, false);
		panelCoord2 = new CoordSelectionPanel(CGConstants.PANELID_COORDS_END, "GUI:particle.end", true, false);
		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);
		panelEntity.setEnabled(false);
		panelEntity.setEnabledContent(false);
	}

	@Override
	protected void createListeners()
	{
		checkBoxEntity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panelEntity.setEnabled(checkBoxEntity.isSelected());
				panelEntity.setEnabledContent(checkBoxEntity.isSelected());
			}
		});
	}

	@Override
	public String generateCommand()
	{
		Particle particle = panelParticle.generateParticle();
		Coordinates coord1 = panelCoord1.generateCoord();
		Coordinates coord2 = panelCoord2.generateCoord();
		int speed = panelParticle.getSpeed();

		if (particle == null || coord1 == null || coord2 == null || speed < 0) return null;

		String command = "particle " + particle.getId();
		if (particle.getParticleType() == Particle.BLOCK || particle.getParticleType() == Particle.ITEM) command += "_"
				+ panelParticle.getItemProperties(particle.getParticleType() == Particle.ITEM);

		command += " " + coord1.commandStructure() + " " + coord2.commandStructure() + " " + Integer.toString(speed);

		if (panelParticle.getIsCount() || checkBoxEntity.isSelected())
		{
			int count = panelParticle.getCount();
			if (count < 0) return null;
			command += " " + Integer.toString(count);
		}

		if (checkBoxEntity.isSelected())
		{
			Target entity = panelEntity.generateEntity();
			if (entity == null) return null;
			command += " " + entity.commandStructure();
		}

		return command;

	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		panelEntity.setEnabledContent((boolean) data.get(CGConstants.DATAID_CHECK));
	}

}
