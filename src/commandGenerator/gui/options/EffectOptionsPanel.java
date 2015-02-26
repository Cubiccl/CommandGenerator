package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Effect;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.EffectSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.Constants;

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
		panelEntity = new TargetSelectionPanel(Constants.PANELID_TARGET, "GENERAL:target.entity", Constants.ENTITIES_ALL);
		panelEffect = new EffectSelectionPanel(Constants.PANELID_EFFECT, "GENERAL:effect");
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
