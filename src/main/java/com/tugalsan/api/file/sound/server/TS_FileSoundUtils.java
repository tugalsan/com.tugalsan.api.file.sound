package com.tugalsan.api.file.sound.server;

import com.tugalsan.api.thread.server.safe.TS_ThreadSafeTrigger;
import com.tugalsan.api.thread.server.struct.TS_ThreadStructBuilder;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.awt.Toolkit;
import java.nio.file.Path;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class TS_FileSoundUtils {

    public static void playSound(TS_ThreadSafeTrigger killTrigger, Path soundFile) {
        TS_ThreadStructBuilder.of()//I KNOW
                .init(() -> {
                    return TGS_UnSafe.call(() -> {
                        try (var inputStream = AudioSystem.getAudioInputStream(soundFile.toFile());) {
                            var clip = AudioSystem.getClip();
                            clip.open(inputStream);
                            clip.start();
                            return clip;
                        }
                    });
                })
                .main((kt2, clip) -> {
                    //I NEED THIS EMPTY RUN
                })
                .fin(clip -> ((Clip) clip).stop())
                .cycle_mainValidation(clip -> clip != null && ((Clip) clip).isRunning() && killTrigger.hasNotTriggered())
                .asyncRun();
    }

    public static void beep() {
        Toolkit.getDefaultToolkit().beep();
    }
}
