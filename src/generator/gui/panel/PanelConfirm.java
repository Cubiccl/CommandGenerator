package generator.gui.panel;

import generator.CommandGenerator;
import generator.gui.button.CButton;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.interfaces.IConfirmState;

@SuppressWarnings("serial")
public class PanelConfirm extends CPanel implements IClickEvent
{
	private static final int CANCEL = 0, OK = 1;

	private CButton buttonOk, buttonCancel;
	private CPanel component;
	private IConfirmState listener;

	public PanelConfirm(CPanel component, IConfirmState listener)
	{
		this.component = component;
		this.listener = listener;
		this.buttonCancel = new CButton("GUI:state.cancel");
		this.buttonOk = new CButton("GUI:state.ok");

		this.buttonCancel.addActionListener(new ClickEvent(this, CANCEL));
		this.buttonOk.addActionListener(new ClickEvent(this, OK));

		this.gbc.gridwidth = 2;
		this.add(this.component, this.gbc);

		this.gbc.insets.top = 40;
		this.gbc.gridwidth = 1;
		this.gbc.gridy++;
		this.add(this.buttonOk, this.gbc);

		this.gbc.gridx++;
		this.add(this.buttonCancel, this.gbc);
	}

	@Override
	public void onClick(int componentID)
	{
		if (this.listener.confirm(componentID == CANCEL, this.component)) CommandGenerator.clearActiveState();
	}

}