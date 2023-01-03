package com.keyboards.sound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	AudioInputStream inputStream;
	Clip clip;
	
	public Sound(String filePath) {
		setFile(filePath);
	}
	
	private void setFile(String filePath) {
			try {
				inputStream = AudioSystem.getAudioInputStream(new File(filePath));
				clip = AudioSystem.getClip();
				// clip.open(inputStream);
			} catch(Exception e) {
				System.out.println("ERROR: Sound file not found " + filePath);
				e.printStackTrace();
			}
	}
	
	public void play() {
		try {
			if (!clip.isOpen()) {
				clip.open(inputStream);
			}
			clip.start();
			if (!clip.isActive()) {
				clip.setFramePosition(0);
			}
		} catch (Exception e) {
			System.out.println("ERROR: unable to open clip");
			e.printStackTrace();
		}
	}
	
	public void stop() {
		clip.stop();
		clip.setFramePosition(0);
	}
	
	public void loop() {
		try {
			if (!clip.isOpen()) {
				clip.open(inputStream);
			}
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			System.out.println("ERROR: unable to open clip");
			e.printStackTrace();
		}
	}
}


