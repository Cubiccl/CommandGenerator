package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Effect;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.EffectSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class EffectOptionsPanel extends OptionsPanel
{

	private EntitySelectionPanel panelEntity;
	private EffectSelectionPanel panelEffect;

	public EffectOptionsPanel()
	{
		super();

		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);
		panelEffect = new EffectSelectionPanel(CGConstants.PANELID_EFFECT, "GENERAL:effect");

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panelEntity);
		gbc.gridy++;
		add(panelEffect);
	}

	@Override
	public String generateCommand()
	{
		Target entity = panelEntity.generateEntity();
		Effect effect = panelEffect.generateEffect();

		if (entity == null || effect == null) return null;

		return "effect " + entity.commandStructure() + " " + effect.commandStructure();
	}

}
