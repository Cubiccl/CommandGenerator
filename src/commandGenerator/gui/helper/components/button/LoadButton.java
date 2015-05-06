package commandGenerator.gui.helper.components.button;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.SavedObjects;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.gui.helper.components.listeners.ClickListener;
import commandGenerator.gui.helper.components.listeners.IClick;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class LoadButton extends CButton implements IClick
{

	private ISave parent;
	private byte type;

	public LoadButton(byte type, ISave parent)
	{
		super("GENERAL:load");
		this.type = type;
		this.parent = parent;
		this.addActionListener(new ClickListener(this));
	}

	@Override
	public void click()
	{
		if (SavedObjects.getList(this.type).size() == 0)
		{
			DisplayHelper.showMessage(new CLabel("GENERAL:load_none"), Generator.translate("GENERAL:load"));
			return;
		}
		Object object = SavedObjects.askObjectToLoad(this.type);
		if (object == null) return;
		this.parent.load(object);
	}

}
