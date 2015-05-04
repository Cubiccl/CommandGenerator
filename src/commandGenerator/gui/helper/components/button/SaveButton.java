package commandGenerator.gui.helper.components.button;

import javax.swing.JLabel;

import commandGenerator.arguments.objects.SavedObjects;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.gui.helper.components.listeners.ClickListener;
import commandGenerator.gui.helper.components.listeners.IClick;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class SaveButton extends CButton implements IClick
{

	private ISave parent;
	private byte type;

	public SaveButton(byte type, ISave parent)
	{
		super("GENERAL:save");
		this.type = type;
		this.parent = parent;
		this.addActionListener(new ClickListener(this));
	}

	@Override
	public void click()
	{
		Object object = this.parent.getObjectToSave();
		String name = DisplayHelper.askObjectName(this.type);
		if (name == null) return;
		SavedObjects.add(name, this.type, object);
		DisplayHelper.showMessage(new JLabel(Lang.get("GENERAL:save_success").replaceAll("<item>", name)), Lang.get("GENERAL:save"));
	}

}
