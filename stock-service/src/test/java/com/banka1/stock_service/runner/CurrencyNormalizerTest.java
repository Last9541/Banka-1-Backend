package com.banka1.stock_service.runner;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link CurrencyNormalizer}.
 */
class CurrencyNormalizerTest {

    @Test
    void normalizeMapsKnownLongFormToIsoCode() {
        assertThat(CurrencyNormalizer.normalize("United States Dollar")).isEqualTo("USD");
        assertThat(CurrencyNormalizer.normalize("British Pound Sterling")).isEqualTo("GBP");
        assertThat(CurrencyNormalizer.normalize("Euro")).isEqualTo("EUR");
        assertThat(CurrencyNormalizer.normalize("Japanese Yen")).isEqualTo("JPY");
        assertThat(CurrencyNormalizer.normalize("Swiss Franc")).isEqualTo("CHF");
        assertThat(CurrencyNormalizer.normalize("Canadian Dollar")).isEqualTo("CAD");
        assertThat(CurrencyNormalizer.normalize("Australian Dollar")).isEqualTo("AUD");
        assertThat(CurrencyNormalizer.normalize("Serbian Dinar")).isEqualTo("RSD");
    }

    @Test
    void normalizeIsCaseInsensitiveForAliases() {
        assertThat(CurrencyNormalizer.normalize("united states dollar")).isEqualTo("USD");
        assertThat(CurrencyNormalizer.normalize("UNITED STATES DOLLAR")).isEqualTo("USD");
        assertThat(CurrencyNormalizer.normalize("  British Pound Sterling  ")).isEqualTo("GBP");
    }

    @Test
    void normalizeReturnsAlreadyIsoCodeUntouched() {
        assertThat(CurrencyNormalizer.normalize("USD")).isEqualTo("USD");
        assertThat(CurrencyNormalizer.normalize("usd")).isEqualTo("USD");
    }

    @Test
    void normalizePassesThroughUnknownLabel() {
        // Unknown currencies are not coerced; they bubble up unchanged so importers
        // can flag them via isSupported() and act on the original value.
        assertThat(CurrencyNormalizer.normalize("Indonesian Rupiah")).isEqualTo("Indonesian Rupiah");
        assertThat(CurrencyNormalizer.normalize("Polish Zloty")).isEqualTo("Polish Zloty");
    }

    @Test
    void normalizeReturnsNullForBlankInput() {
        assertThat(CurrencyNormalizer.normalize(null)).isNull();
        assertThat(CurrencyNormalizer.normalize("")).isNull();
        assertThat(CurrencyNormalizer.normalize("   ")).isNull();
    }

    @Test
    void isSupportedRecognizesEightConfiguredCurrencies() {
        assertThat(CurrencyNormalizer.isSupported("USD")).isTrue();
        assertThat(CurrencyNormalizer.isSupported("United States Dollar")).isTrue();
        assertThat(CurrencyNormalizer.isSupported("EUR")).isTrue();
        assertThat(CurrencyNormalizer.isSupported("RSD")).isTrue();
        assertThat(CurrencyNormalizer.isSupported("CHF")).isTrue();
    }

    @Test
    void isSupportedRejectsCurrenciesAccountServiceCannotModel() {
        assertThat(CurrencyNormalizer.isSupported("Indonesian Rupiah")).isFalse();
        assertThat(CurrencyNormalizer.isSupported("Indian Rupee")).isFalse();
        assertThat(CurrencyNormalizer.isSupported("Polish Zloty")).isFalse();
        assertThat(CurrencyNormalizer.isSupported(null)).isFalse();
        assertThat(CurrencyNormalizer.isSupported("")).isFalse();
    }
}
