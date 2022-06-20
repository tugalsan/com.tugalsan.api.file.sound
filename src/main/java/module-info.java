module com.tugalsan.api.file.sound {
    requires java.desktop;
    requires gwt.user;
    requires com.tugalsan.api.log;
    requires com.tugalsan.api.executable;
    requires com.tugalsan.api.thread;
    exports com.tugalsan.api.file.sound.client;
    exports com.tugalsan.api.file.sound.server;
}
