package com.example.video;

import java.util.HashMap;
import java.util.Map;

public class SharpenAdapter {
	private final LegacySharpen legacy;
	private final Map<String, Frame[]> handleToFrames;
	private int handleSeq;

	public SharpenAdapter() {
		this.legacy = new LegacySharpen();
		this.handleToFrames = new HashMap<>();
		this.handleSeq = 0;
	}

	public Frame[] sharpen(Frame[] frames, int strength) {
		String inputHandle = createHandle(frames);
		String outputHandle = legacy.applySharpen(inputHandle, strength);
		// For this simulation, map the output handle to the same (mutated) frames
		// to represent the sharpening result.
		handleToFrames.put(outputHandle, frames);
		return handleToFrames.get(outputHandle);
	}

	private String createHandle(Frame[] frames) {
		String handle = "H" + (++handleSeq);
		handleToFrames.put(handle, frames);
		return handle;
	}
}
