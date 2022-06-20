package com.tugalsan.api.file.sound.client;

import com.google.gwt.media.client.Audio;
import com.tugalsan.api.log.client.TGC_Log;

public class TGC_SoundWav {

    final private static TGC_Log d = TGC_Log.of(TGC_SoundWav.class.getSimpleName());

    private final Audio bip;
    private String url;

    final public boolean isSupported() {
        return bip != null;
    }

    final public void play() {
        if (bip != null) {
            bip.play();
        }
    }

    final public String getUrl() {
        return url;
    }

    final public void setSrc(CharSequence url) {
        this.url = url.toString();
        if (bip != null) {
            bip.setSrc(this.url);
        }
    }

    public TGC_SoundWav() {
        this(null);
    }

    public TGC_SoundWav(CharSequence url) {
        bip = Audio.createIfSupported();
        if (!isSupported()) {
            d.ce("constructor", "Sesli uyarı desteklenmiyor.");
            return;
        }
        d.ci("constructor", "Sesli uyarı destekleniyor.");
        if (url != null) {
            setSrc(url);
        }
    }
}
