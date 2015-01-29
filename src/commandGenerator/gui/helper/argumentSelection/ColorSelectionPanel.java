package commandGenerator.gui.helper.argumentSelection;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class ColorSelectionPanel extends HelperPanel
{

	private JLabel labelColor;
	private CLabel labelRed, labelGreen, labelBlue;
	private JSlider sliderRed, sliderGreen, sliderBlue;
	private JPanel panelColor;

	public ColorSelectionPanel(String title)
	{
		super(CGConstants.NBTID_COLOR, title, 600, 200);

		labelColor = new JLabel("");
		labelRed = new CLabel("GUI:color.red");
		labelGreen = new CLabel("GUI:color.green");
		labelBlue = new CLabel("GUI:color.blue");

		sliderRed = new JSlider(0, 255);
		sliderRed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				setLabelColor();
			}
		});
		sliderGreen = new JSlider(0, 255);
		sliderGreen.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				setLabelColor();
			}
		});
		sliderBlue = new JSlider(0, 255);
		sliderBlue.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				setLabelColor();
			}
		});

		panelColor = new JPanel();
		panelColor.setPreferredSize(new Dimension(150, 150));
		panelColor.setBackground(new Color(sliderRed.getValue(), sliderGreen.getValue(), sliderBlue.getValue()));
		panelColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panelColor.add(labelColor);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(labelRed);
		gbc.gridy++;
		add(labelGreen);
		gbc.gridy++;
		add(labelBlue);
		gbc.gridy++;
		add(Box.createGlue());

		gbc.gridx++;
		gbc.gridy = 0;
		add(sliderRed);
		gbc.gridy++;
		add(sliderGreen);
		gbc.gridy++;
		add(sliderBlue);

		gbc.gridx++;
		gbc.gridy = 0;
		gbc.gridheight = 4;
		add(panelColor);
		gbc.gridheight = 1;
	}

	public int getColor()
	{
		return (sliderRed.getValue() << 16) + (sliderGreen.getValue() << 8) + (sliderBlue.getValue());
	}

	public void setEnabledContent(boolean enable)
	{
		setEnabled(enable);
		panelColor.setEnabled(enable);
		labelRed.setEnabled(enable);
		labelGreen.setEnabled(enable);
		labelBlue.setEnabled(enable);
		sliderRed.setEnabled(enable);
		sliderGreen.setEnabled(enable);
		sliderBlue.setEnabled(enable);
	}

	public void setLabelColor()
	{
		panelColor.setBackground(new Color(sliderRed.getValue(), sliderGreen.getValue(), sliderBlue.getValue()));
	}

}
