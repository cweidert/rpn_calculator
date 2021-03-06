package com.heliomug.calculator.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.heliomug.calculator.Calculator;
import com.heliomug.calculator.Macro;

@SuppressWarnings("serial")
public class PanelStatus extends JPanel{
	public PanelStatus() {
		super();
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Frame.BORDER_COLOR));
		

		StatusLabel label;
		JPanel subpanel;

		subpanel = new JPanel();
		subpanel.setLayout(new BorderLayout());
		label = new StatusLabel(() -> Frame.getCalculator().getAngleMode().getLetter());
		subpanel.add(label, BorderLayout.WEST);
		label = new StatusLabel(() -> String.format("%5s", Frame.getCalculator().getStatus()));
		subpanel.add(label, BorderLayout.EAST);
		add(subpanel, BorderLayout.WEST);

		subpanel = new JPanel();
		subpanel.setLayout(new BorderLayout());
		label = new StatusLabel(() -> {
			String name = "";
			Calculator calc = Frame.getCalculator();
			Macro macro = calc.getCurrentMacro();
			if (macro == null) {
				name = "[none]";
			} else if (calc.isRecording()) {
				name = "<rec>";
			} else {
				name = macro.getName();
			}
			return "macro: " + name;
		});
		subpanel.add(label, BorderLayout.WEST);
		label = new StatusLabel(() -> Frame.getFrame().getCurrentString()) {
			@Override
			public void paint(Graphics g) {
				if (Frame.getFrame().getMode() == Mode.STANDARD) {
					setBackground(Frame.INACTIVE_COLOR);
				} else {
					setBackground(Frame.ACTIVE_COLOR);
				}
				setOpaque(true);
				super.paint(g);
			}
		};
		subpanel.add(label, BorderLayout.CENTER);
		add(subpanel, BorderLayout.CENTER);
	}
	
	private class StatusLabel extends JLabel {
		Supplier<String> textSupplier;

		public StatusLabel(Supplier<String> sup) {
			super("   ");
			textSupplier = () -> String.format(" %s ", sup.get());
			setHorizontalAlignment(SwingConstants.RIGHT);
			setFont(Frame.MESSAGE_FONT);
			setBorder(BorderFactory.createLineBorder(Frame.BORDER_COLOR));
		}

		@Override
		public void paint(Graphics g) {
			String text = textSupplier.get();
			if (text.length() == 0) {
				setText("     ");
			} else {
				setText(text);
			}
			super.paint(g);
		}
	}
}
