package com.tugalsan.api.file.sound.server;

import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import com.tugalsan.api.thread.server.async.builder.TS_ThreadAsyncBuilder;
import com.tugalsan.api.function.client.maythrowexceptions.checked.TGS_FuncMTCUtils;
import com.tugalsan.api.log.server.TS_Log;
import java.awt.Toolkit;
import java.nio.file.Path;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class TS_FileSoundUtils {

    private TS_FileSoundUtils() {

    }

    final private static TS_Log d = TS_Log.of(TS_FileSoundUtils.class);

    public static void playSound(String name, TS_ThreadSyncTrigger killTrigger, Path soundFile) {
        TS_ThreadAsyncBuilder.<Clip>of(killTrigger.newChild(d.className()).newChild("playSound"))
                .name(name)
                .init(() -> {
                    return TGS_FuncMTCUtils.call(() -> {
                        try (var inputStream = AudioSystem.getAudioInputStream(soundFile.toFile());) {
                            var clip = AudioSystem.getClip();
                            clip.open(inputStream);
                            clip.start();
                            return clip;
                        }
                    });
                })
                .mainEmpty()
                .fin(clip -> clip.stop())
                .cycle_mainValidation((kt, clip) -> clip != null && clip.isRunning() && kt.hasNotTriggered())
                .asyncRun();
    }

    public static void beep() {
        Toolkit.getDefaultToolkit().beep();
    }
}
