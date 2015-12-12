package generator.gui.panel;

import generator.CommandGenerator;
import generator.gui.CLabel;
import generator.gui.combobox.CCombobox;

@SuppressWarnings("serial")
public class PanelSettings extends CPanel
{
	/** Creates a new PanelSettings and displays it. */
	public static void create()
	{
		if (!CommandGenerator.getActiveState().getName().equals(CommandGenerator.translate("GUI:menu.settings")))
		{
			PanelSettings panel = new PanelSettings();
			CommandGenerator.addState("GUI:menu.settings", panel);
		}
	}
	private CCombobox comboboxLanguage;

	private CLabel labelLanguage;

	public PanelSettings()
	{
		super();

		this.labelLanguage = new CLabel("GUI:settings.language");
		this.comboboxLanguage = new CCombobox(new String[0]);

		this.add(this.labelLanguage, this.gbc);
		this.gbc.gridx++;
		this.add(this.comboboxLanguage, this.gbc);
		this.updateLang();
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		String[][] langs = CommandGenerator.getSettings().getAvailableLanguages();
		String[] names = new String[langs.length];
		for (int i = 0; i < names.length; i++)
		{
			names[i] = langs[i][1];
		}
		this.comboboxLanguage.setValues(names);
	}

}
