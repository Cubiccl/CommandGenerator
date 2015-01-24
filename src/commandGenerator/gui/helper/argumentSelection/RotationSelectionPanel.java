package commandGenerator.gui.helper.argumentSelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagFloat;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.HelperPanel;

@SuppressWarnings("serial")
public class RotationSelectionPanel extends HelperPanel
{

	private CLabel labelX, labelY;
	private JSpinner spinnerX, spinnerY;

	public RotationSelectionPanel(String id, String title)
	{
		super(id, title, 200, 200);

		labelX = new CLabel("GUI:rotation.horizontal");
		labelY = new CLabel("GUI:rotation.vertical");

		spinnerX = new JSpinner(new SpinnerNumberModel(0, 0, 360, 1));
		spinnerY = new JSpinner(new SpinnerNumberModel(0, -90, 90, 1));

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(labelX, gbc);
		gbc.gridy++;
		add(labelY, gbc);

		gbc.gridx++;
		gbc.gridy--;
		add(spinnerX, gbc);
		gbc.gridy++;
		add(spinnerY, gbc);
	}

	public List<Tag> getRotations()
	{
		List<Tag> rot = new ArrayList<Tag>();
		rot.add(new TagFloat().setValue(Float.parseFloat(Integer.toString((int) spinnerX.getValue()))));
		rot.add(new TagFloat().setValue(Float.parseFloat(Integer.toString((int) spinnerY.getValue()))));
		return rot;
	}

	public void setEnabledContent(boolean enable)
	{
		this.setEnabled(enable);
		labelX.setEnabled(enable);
		labelY.setEnabled(enable);
		spinnerX.setEnabled(enable);
		spinnerY.setEnabled(enable);
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		Coordinates coord = (Coordinates) data.get(getPanelId());
		spinnerX.setValue(coord.getRot(Coordinates.X));
		spinnerY.setValue(coord.getRot(Coordinates.Y));
	}

}
