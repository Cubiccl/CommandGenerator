package commandGenerator.gui.helper.argumentSelection;

import java.util.Map;

import javax.swing.JLabel;

import commandGenerator.arguments.objects.Effect;
import commandGenerator.arguments.objects.EffectType;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CComboBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.IBox;
import commandGenerator.gui.helper.components.NumberSpinner;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class EffectSelectionPanel extends HelperPanel implements IBox
{

	private JLabel labelImage;
	private CEntry entryDuration;
	private NumberSpinner spinnerAmplifier;
	private CComboBox comboboxEffect;
	private CCheckBox checkboxHideParticles;

	public EffectSelectionPanel(String id, String title)
	{
		super(id, title, 500, 200);

		labelImage = new JLabel();
		try
		{
			labelImage.setIcon(Registerer.getObjectFromId("speed").getTexture());
		} catch (Exception e)
		{
			DisplayHelper.missingTexture("effects/speed.png");
		}

		entryDuration = new CEntry(CGConstants.DATAID_NONE, "GUI:effect.duration");

		spinnerAmplifier = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:effect.amplifier", 1, 256, null);

		comboboxEffect = new CComboBox(CGConstants.DATAID_NONE, "GUI:effect.choose", Registerer.getObjectList(CGConstants.OBJECT_EFFECT), this);

		checkboxHideParticles = new CCheckBox(CGConstants.DATAID_NONE, "GUI:effect.hide");

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(comboboxEffect, gbc);
		gbc.gridy++;
		add(entryDuration, gbc);
		gbc.gridy++;
		add(spinnerAmplifier, gbc);
		gbc.gridy++;
		add(checkboxHideParticles, gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		gbc.gridheight = 4;
		add(labelImage, gbc);
		gbc.gridheight = 1;
	}

	public Effect generateEffect()
	{
		DisplayHelper.log("Generating effect");
		String duration = entryDuration.getText();
		int amplifier = spinnerAmplifier.getValue();

		try
		{
			int test = Integer.parseInt(duration);
			if (test < 0)
			{
				DisplayHelper.warningPositiveInteger();
				return null;
			}
		} catch (Exception ex)
		{
			DisplayHelper.warningInteger();
			return null;
		}

		return new Effect((EffectType) comboboxEffect.getValue(), amplifier - 1, Integer.parseInt(duration), !checkboxHideParticles.isSelected());
	}

	@Override
	public void updateLang()
	{
		updateTitle();
		spinnerAmplifier.updateLang();
		entryDuration.updateLang();
		checkboxHideParticles.updateLang();
		comboboxEffect.updateLang();
	}

	@Override
	public void updateCombobox()
	{
		try
		{
			labelImage.setIcon(comboboxEffect.getValue().getTexture());
		} catch (Exception e)
		{
			DisplayHelper.missingTexture(comboboxEffect.getValue().getTexture().toString());
		}
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		Effect effect = (Effect) data.get(getPanelId());

		entryDuration.setTextField(Integer.toString(effect.getDuration()));
		spinnerAmplifier.setSelected(effect.getAmplifier());
		comboboxEffect.setSelected(effect.getType());
		checkboxHideParticles.setSelected(!effect.showParticles());
	}

}
