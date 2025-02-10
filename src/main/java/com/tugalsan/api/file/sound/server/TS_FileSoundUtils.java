package com.tugalsan.api.file.sound.server;

import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import com.tugalsan.api.thread.server.async.builder.TS_ThreadAsyncBuilder;
import com.tugalsan.api.function.client.maythrow.checkedexceptions.TGS_FuncMTCEUtils;
import java.awt.Toolkit;
import java.nio.file.Path;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class TS_FileSoundUtils {

    public static void playSound(TS_ThreadSyncTrigger killTrigger, Path soundFile) {
        TS_ThreadAsyncBuilder.of()//I KNOW
                .init(() -> {
                    return TGS_FuncMTCEUtils.call(() -> {
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
