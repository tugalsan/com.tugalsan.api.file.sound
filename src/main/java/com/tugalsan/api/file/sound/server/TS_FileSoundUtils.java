package com.tugalsan.api.file.sound.server;

import com.tugalsan.api.runnable.client.TGS_RunnableType1;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import com.tugalsan.api.thread.server.async.TS_ThreadAsyncBuilder;
import com.tugalsan.api.union.client.TGS_UnionExcuse;
import java.awt.Toolkit;
import java.io.IOException;
import java.nio.file.Path;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TS_FileSoundUtils {

    public static void playSound(TS_ThreadSyncTrigger killTrigger, Path soundFile, TGS_RunnableType1<Throwable> onError) {
        TS_ThreadAsyncBuilder.of()//I KNOW
                .init(() -> {
                    try (var inputStream = AudioSystem.getAudioInputStream(soundFile.toFile());) {
                        var clip = AudioSystem.getClip();
                        clip.open(inputStream);
                        clip.start();
                        return TGS_UnionExcuse.of(clip);
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        return TGS_UnionExcuse.ofExcuse(ex);
                    }
                })
                .main((kt2, u_clip) -> {
                })
                .fin(u_clip -> {
                    var u = ((TGS_UnionExcuse<Clip>) u_clip);
                    if (u.isPresent()) {
                        u.value().stop();
                    }
                })
                .cycle_mainValidation(u_clip -> {
                    if (killTrigger.hasTriggered()) {
                        return false;
                    }
                    var u = ((TGS_UnionExcuse<Clip>) u_clip);
                    if (u.isExcuse()) {
                        onError.run(u.excuse());
                        return false;
                    }
                    return u.value().isRunning();
                })
                .asyncRun();
    }

    public static void beep() {
        Toolkit.getDefaultToolkit().beep();
    }
}
