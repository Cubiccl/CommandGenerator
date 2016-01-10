package generator.gui.panel;

import generator.CommandGenerator;
import generator.gui.CLabel;
import generator.gui.combobox.CCombobox;
import generator.interfaces.IConfirmState;
import generator.main.Settings;
import generator.main.Text;

@SuppressWarnings("serial")
public class PanelSettings extends CPanel implements IConfirmState
{
	/** Creates a new PanelSettings and displays it. */
	public static void create()
	{
		if (!(CommandGenerator.getActiveState().getComponent() instanceof LoadingPanel))
		{
			PanelSettings panel = new PanelSettings();
			CommandGenerator.addStateWithConfirm(new Text("GUI", "menu.settings"), panel, panel);
		}
	}

	private CCombobox comboboxLanguage;
	private CLabel labelLanguage;

	public PanelSettings()
	{
		super();

		this.labelLanguage = new CLabel(new Text("GUI", "settings.language"));
		this.comboboxLanguage = new CCombobox(new String[0]);

		this.add(this.labelLanguage, this.gbc);
		this.gbc.gridx++;
		this.add(this.comboboxLanguage, this.gbc);
		this.updateLang();
	}

	@Override
	public boolean confirm(boolean cancel, CPanel component)
	{
		if (!cancel)
		{
			String[][] langs = CommandGenerator.getSettings().getAvailableLanguages();
			String language = langs[0][0];
			for (int i = 0; i < langs.length; i++)
			{
				if (langs[i][1].equals(this.comboboxLanguage.getSelectedItem())) language = langs[i][0];
			}
			CommandGenerator.getSettings().setSetting(Settings.LANGUAGE, language);
		}
		return true;
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		String[][] langs = CommandGenerator.getSettings().getAvailableLanguages();
		String[] names = new String[langs.length];
		int selected = 0;
		for (int i = 0; i < names.length; i++)
		{
			names[i] = langs[i][1];
			if (langs[i][0].equals(CommandGenerator.getSettings().getLanguageId())) selected = i;
		}
		this.comboboxLanguage.setValues(names);
		this.comboboxLanguage.setSelectedIndex(selected);
	}

}
