package com.synlabs.qc.impl.style;

import com.synlabs.qc.impl.style.Style;

import java.awt.*;

public class V3Style extends Style {
	private static final Dimension INPUTFIELD_SIZE = new Dimension(190, 24);

	@Override
	public Dimension getInputfieldSize() {
		return INPUTFIELD_SIZE;
	}
}
