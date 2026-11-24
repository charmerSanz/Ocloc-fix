package dev.locatorfix;

/** Used only by the render thread. No world data is retained. */
public final class FrameState {
    private static boolean rendered;
    private FrameState() {}
    public static void begin() { rendered = false; }
    public static void markRendered() { rendered = true; }
    public static boolean rendered() { return rendered; }
}
