package com.keyboards.sound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	
	public Sound(String filePath) {
		setFile(filePath);
	}
	
	private void setFile(String filePath) {
			try {
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(filePath));
				clip = AudioSystem.getClip();
				clip.open(inputStream);
			} catch(Exception e) {
				System.out.println("Sound file not found " + filePath);
				e.printStackTrace();
			}
	}
	
	public void play() {
		clip.start();
	}
	
	public void stop() {
		clip.stop();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}


