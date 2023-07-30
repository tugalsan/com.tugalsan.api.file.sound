package com.tugalsan.api.file.sound.server;

import com.tugalsan.api.thread.server.async.TS_ThreadAsync;
import java.awt.Toolkit;
import java.nio.file.Path;
import javax.sound.sampled.AudioSystem;
import com.tugalsan.api.unsafe.client.*;

public class TS_FileSoundUtils {

    public static void playSound(Path soundFile) {
        TS_ThreadAsync.now(() -> {
            TGS_UnSafe.run(() -> {
                try ( var inputStream = AudioSystem.getAudioInputStream(soundFile.toFile());) {
                    var clip = AudioSystem.getClip();
                    clip.open(inputStream);
                    clip.start();
                }
            });
        });
    }

    public static void beep() {
        Toolkit.getDefaultToolkit().beep();
    }
}
