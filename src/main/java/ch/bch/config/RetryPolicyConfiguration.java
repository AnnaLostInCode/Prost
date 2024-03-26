/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2018.
 */

package ch.bch.config;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * Exponential backoff retry policy config
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class RetryPolicyConfiguration {

    /**
     * Maximum number of attempts to send a request before giving up.
     */
    @NotNull
    private int maxAttempts;

    /**
     * Milliseconds to wait after first failure.
     */
    @NotNull
    private long initialWaitMs;

    /**
     * Maximum milliseconds to wait.
     */
    @NotNull
    private long maxWaitMs;

    /**
     * Backoff factor after each try.
     */
    @NotNull
    private int backoffFactor;

}
