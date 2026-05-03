package com.banka1.stock_service.runner;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Normalizes free-form currency labels coming from external seed sources
 * (CSV files, third-party APIs) into the ISO 4217 codes used internally
 * by the rest of the platform.
 *
 * <p>The bank's account-service exposes only a fixed set of ISO codes via the
 * {@code CurrencyCode} enum. When a listing is exposed to the frontend or to
 * order-service with a non-ISO label such as {@code "United States Dollar"},
 * downstream calls (e.g. resolving the bank account in that currency) fail
 * with a parameter binding error. Persisting the normalized ISO code at
 * import time keeps the rest of the system on a clean contract regardless of
 * how the upstream feed encodes the value.
 *
 * <p>Currencies that are not part of the supported set are reported via
 * {@link #isSupported(String)} so callers can decide how to react (e.g.
 * mark the parent record as inactive instead of silently corrupting it).
 */
public final class CurrencyNormalizer {

    /**
     * Maps the free-form currency labels observed in the seed CSV to their
     * canonical ISO 4217 codes. Keys are matched case-insensitively.
     */
    private static final Map<String, String> ALIASES = Map.ofEntries(
            Map.entry("us dollar", "USD"),
            Map.entry("u.s. dollar", "USD"),
            Map.entry("united states dollar", "USD"),
            Map.entry("american dollar", "USD"),
            Map.entry("dollar", "USD"),
            Map.entry("euro", "EUR"),
            Map.entry("euros", "EUR"),
            Map.entry("british pound", "GBP"),
            Map.entry("british pound sterling", "GBP"),
            Map.entry("pound sterling", "GBP"),
            Map.entry("japanese yen", "JPY"),
            Map.entry("yen", "JPY"),
            Map.entry("swiss franc", "CHF"),
            Map.entry("canadian dollar", "CAD"),
            Map.entry("australian dollar", "AUD"),
            Map.entry("serbian dinar", "RSD"),
            Map.entry("dinar", "RSD")
    );

    /**
     * The currency codes the account-service is able to model. Any other
     * code is considered unsupported by the bank even if it is a valid ISO
     * 4217 symbol.
     */
    private static final Set<String> SUPPORTED_ISO_CODES = Set.copyOf(ForexSupportedCurrencies.values());

    private CurrencyNormalizer() {
    }

    /**
     * Returns the ISO 4217 code that corresponds to {@code raw} when one is
     * known. Already-normalized codes (such as {@code "USD"}) and unknown
     * labels are returned unchanged so the importer can still surface the
     * original value through {@link #isSupported(String)}.
     *
     * @param raw value as it appears in the upstream feed
     * @return normalized ISO code, or the trimmed original when no alias is
     *         configured; {@code null} when the input is {@code null} or blank
     */
    public static String normalize(String raw) {
        if (raw == null) {
            return null;
        }
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        String upper = trimmed.toUpperCase(Locale.ROOT);
        if (SUPPORTED_ISO_CODES.contains(upper)) {
            return upper;
        }
        String alias = ALIASES.get(trimmed.toLowerCase(Locale.ROOT));
        return alias != null ? alias : trimmed;
    }

    /**
     * Reports whether the supplied value resolves to a currency the
     * account-service is able to handle. Used by importers to decide whether
     * the parent record should be marked active.
     *
     * @param value raw or normalized currency value
     * @return {@code true} when the normalized value is in the supported set
     */
    public static boolean isSupported(String value) {
        String normalized = normalize(value);
        return normalized != null && SUPPORTED_ISO_CODES.contains(normalized);
    }
}
