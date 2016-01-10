package generator.gui.combobox;

import generator.interfaces.ITranslate;
import generator.main.Text;

/** A Combobox that translates is values. */
@SuppressWarnings("serial")
public class CChoiceCombobox extends CCombobox implements ITranslate
{

	/** The values to display */
	private Text[] choices;
	/** The ID for translations */
	private String id;
	/** The values */
	private String[] values;

	/** Creates a new ChoiceCombobox.
	 * 
	 * @param id - The ID for translations.
	 * @param values - The values to choose one of. */
	public CChoiceCombobox(String id, String[] values)
	{
		super(values);
		this.id = id;
		this.values = values;
		this.choices = new Text[this.values.length];
		for (int i = 0; i < this.values.length; i++)
		{
			this.choices[i] = new Text("CHOICE", this.id + "." + this.values[i]);
		}
		this.updateLang();
	}

	/** @return The value the user chose (not its translation). */
	public String getSelectedValue()
	{
		return this.values[this.getSelectedIndex()];
	}

	@Override
	public void setValues(String[] values)
	{
		this.values = values;
		this.updateLang();
	}

	@Override
	public void updateLang()
	{
		String[] translations = new String[this.values.length];
		for (int i = 0; i < translations.length; i++)
			translations[i] = this.choices[i].getValue();
		super.setValues(translations);
	}

}
