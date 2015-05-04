package commandGenerator.gui.helper.components.combobox;

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.gui.helper.components.icomponent.IBox;

@SuppressWarnings("serial")
public class ObjectComboBox extends LabeledSearchBox
{

	private ObjectBase[] objects;

	public ObjectComboBox(String title, ObjectBase[] objects, IBox parent)
	{
		super(title, new String[0], parent);

		String[] names = new String[objects.length];
		for (int i = 0; i < names.length; i++)
			names[i] = objects[i].getName();
		this.setValues(names);

		this.objects = objects;
	}

	/** Returns the selected Object. */
	public ObjectBase getValue()
	{
		String selection = (String) this.getSelectedItem();
		if (selection == null) return null;

		for (ObjectBase object : this.objects)
		{
			if (object.getName().equals(selection)) return object;
		}

		return null;
	}

	public void setData(ObjectBase[] objects)
	{
		this.objects = objects;
		this.reset();
		this.updateLang();
	}

	public void setSelected(ObjectBase object)
	{
		this.reset();
		this.setSelectedItem(object.getName());
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		
		String[] names = new String[this.objects.length];
		for (int i = 0; i < names.length; i++)
			names[i] = this.objects[i].getName();
		this.setValues(names);
	}

}
