package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Effect;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.EffectSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.panel.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class EffectOptionsPanel extends OptionsPanel
{

	private EffectSelectionPanel panelEffect;
	private TargetSelectionPanel panelEntity;

	public EffectOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelEntity);
		add(panelEffect);
	}

	@Override
	protected void createComponents()
	{
		panelEntity = new TargetSelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);
		panelEffect = new EffectSelectionPanel(CGConstants.PANELID_EFFECT, "GENERAL:effect");
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{
		Target entity = panelEntity.generateEntity();
		Effect effect = panelEffect.generateEffect();

		if (entity == null || effect == null) return null;

		return "effect " + entity.commandStructure() + " " + effect.commandStructure();
	}

}
