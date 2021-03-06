package commandGenerator.gui.helper.argumentSelection;

import javax.swing.JLabel;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.Effect;
import commandGenerator.arguments.objects.EffectType;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.button.LoadButton;
import commandGenerator.gui.helper.components.button.SaveButton;
import commandGenerator.gui.helper.components.combobox.ObjectComboBox;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.gui.helper.components.spinner.NumberSpinner;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class EffectSelectionPanel extends CPanel implements IBox, ISave
{

	private CButton buttonSave, buttonLoad;
	private CCheckBox checkboxHideParticles;
	private ObjectComboBox comboboxEffect;
	private CEntry entryDuration;
	private JLabel labelImage;
	private CLabel labelTicks;
	private NumberSpinner spinnerAmplifier;

	public EffectSelectionPanel(String title)
	{
		super(title);

		this.initGui();
	}

	@Override
	protected void addComponents()
	{
		addLine(comboboxEffect, labelImage);
		add(entryDuration);
		add(labelTicks);
		add(spinnerAmplifier);
		add(checkboxHideParticles);
		addLine(buttonSave, buttonLoad);
	}

	@Override
	protected void createComponents()
	{
		ObjectBase[] objects = Generator.registry.getObjectList(ObjectBase.EFFECT);

		labelTicks = new CLabel("GUI:effect.ticks");

		labelImage = new JLabel();
		try
		{
			if (objects.length > 0) labelImage.setIcon(objects[0].getTexture());
		} catch (Exception e)
		{}

		buttonSave = new SaveButton(ObjectBase.EFFECT, this);
		buttonLoad = new LoadButton(ObjectBase.EFFECT, this);

		entryDuration = new CEntry("GUI:effect.duration", "600");

		spinnerAmplifier = new NumberSpinner("GUI:effect.amplifier", 1, 256, null);

		comboboxEffect = new ObjectComboBox("GUI:effect.choose", objects, this);

		checkboxHideParticles = new CCheckBox("GUI:effect.hide");
	}

	@Override
	protected void createListeners()
	{}

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
	public Object getObjectToSave()
	{
		return generateEffect();
	}

	@Override
	public void load(Object object)
	{
		setupFrom((Effect) object);
	}

	public void setupFrom(Effect effect)
	{
		entryDuration.setTextField(Integer.toString(effect.getDuration()));
		spinnerAmplifier.setSelected(effect.getAmplifier());
		comboboxEffect.setSelected(effect.getType());
		checkboxHideParticles.setSelected(!effect.showParticles());
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

}
