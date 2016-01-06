package generator.gui.panel.object;

import generator.CommandGenerator;
import generator.gui.combobox.CChoiceCombobox;
import generator.gui.panel.CPanelVertical;
import generator.gui.textfield.CEntry;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.main.GenerationException;
import generator.registry.instance.Target;
import generator.registry.instance.TargetArgument;

import java.awt.AWTEvent;

import javax.swing.BorderFactory;

/** Allows selecting a Target. */
@SuppressWarnings("serial")
public class PanelTarget extends CPanelVertical implements IClickEvent
{
	private static final int SELECTOR = 0;

	/** Combobox to choose an Argument to add. */
	private CChoiceCombobox comboboxArgument;
	/** Combobox to choose the selector. */
	private CChoiceCombobox comboboxSelector;
	/** Entry to type in a Player's name. */
	private CEntry entryName;
	/** The ID of the title. */
	private String titleId;

	public PanelTarget(String titleId, int targetType)
	{
		super();
		this.titleId = titleId;

		this.comboboxArgument = new CChoiceCombobox("argument", TargetArgument.ARGUMENT_IDS);
		this.comboboxSelector = new CChoiceCombobox("target", Target.SELECTORS);
		this.comboboxSelector.addActionListener(new ClickEvent(this, SELECTOR));

		this.entryName = new CEntry("GUI:target.name");
		this.entryName.setVisible(false);

		this.add(this.comboboxSelector);
		this.add(this.entryName);
		this.add(this.comboboxArgument);

		this.updateLang();
	}

	/** @return The Target input by the user.
	 * @throws GenerationException if a value is not correct. */
	public Target generateTarget() throws GenerationException
	{
		if (this.comboboxSelector.getSelectedValue().equals(Target.PLAYER_NAME))
		{
			if (this.entryName.getText().equals("")) throw new GenerationException(CommandGenerator.translate("GUI:error.missing"));
			if (this.entryName.getText().contains(" ")) throw new GenerationException(CommandGenerator.translate("GUI:error.space"));
			return new Target(this.entryName.getText());
		}
		return new Target(this.comboboxSelector.getSelectedIndex());
	}

	@Override
	public void onEvent(int eventID, AWTEvent event)
	{
		switch (eventID)
		{
			case SELECTOR:
				boolean playerName = this.comboboxSelector.getSelectedValue().equals(Target.SELECTORS[Target.PLAYER_NAME]);
				this.entryName.setVisible(playerName);
				this.comboboxArgument.setVisible(!playerName);
				break;

			default:
				break;
		}
	}

	@Override
	public void updateLang()
	{
		this.setBorder(BorderFactory.createTitledBorder(CommandGenerator.translate(this.titleId)));
	}
}
