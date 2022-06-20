package com.tugalsan.api.file.sound.server;

import java.awt.Toolkit;
import java.nio.file.Path;
import javax.sound.sampled.AudioSystem;
import com.tugalsan.api.thread.server.*;

public class TS_FileSoundUtils {

    public static void playSound(Path soundFile) {
        TS_ThreadRunUtils.once(() -> {
            try ( var inputStream = AudioSystem.getAudioInputStream(soundFile.toFile());) {
                var clip = AudioSystem.getClip();
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void beep() {
        Toolkit.getDefaultToolkit().beep();
    }
}
