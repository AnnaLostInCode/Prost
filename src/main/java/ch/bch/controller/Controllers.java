/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2017.
 */

package ch.bch.controller;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import org.slf4j.Logger;

/**
 * Utility class with common functionality for all controllers.
 */
public final class Controllers {

    public static final String INTERNAL_PATH = "/internal";

    // TODO: change this:
    //  public static final String BETRIEBSPUNKTE = "/betriebspunkte";

    private Controllers() {
        // utility class
    }

    /**
     * Calls the passed method (which must return a {@link CompletableFuture}) and logs any exceptions to the passed logger at error level.
     * <p>
     * This can be useful because if the {@code CompletableFuture} result is not checked, exception might get silently swallowed.
     *
     * @param asyncMethodCall Method to be called. It does not need to be execute asynchronously, but this helper method makes most sense if it is.
     * @param logger          The logger to which the error message will be logged in case of an exception.
     */
    public static void callAsyncWithExceptionLogging(Supplier<? extends CompletableFuture<?>> asyncMethodCall, Logger logger) {
        asyncMethodCall.get()
                .exceptionally(t -> {
                    logger.error("Exception occured in async method", t);
                    return null;
                });
    }

}
