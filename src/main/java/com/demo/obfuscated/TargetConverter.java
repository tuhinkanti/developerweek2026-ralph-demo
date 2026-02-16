package com.demo.obfuscated;

/**
 * Utility class for converting between LegacyTargetCode and TargetKey.
 */
public final class TargetConverter {
    private TargetConverter() {}

    @Deprecated
    public static TargetKey fromLegacy(LegacyTargetCode legacy) {
        return legacy != null ? legacy.toTargetKey() : null;
    }

    @Deprecated
    public static LegacyTargetCode toLegacy(TargetKey key) {
        return LegacyTargetCode.fromTargetKey(key);
    }
}
