package com.demo.obfuscated;

/**
 * Utility class for converting between LegacyTargetCode and TargetKey.
 */
public final class TargetConverter {
    private TargetConverter() {}

    public static TargetKey fromLegacy(LegacyTargetCode legacy) {
        return legacy != null ? legacy.toTargetKey() : null;
    }

    public static LegacyTargetCode toLegacy(TargetKey key) {
        return LegacyTargetCode.fromTargetKey(key);
    }
}
