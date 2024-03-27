package ch.bch.config;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

public final class RetryTemplateBuilder {

    private static final String EXCEPTION_MUST_NOT_BE_NULL = "exception must not be null";

    private final RetryPolicyConfiguration retryPolicyConfig;
    private final Map<Class<? extends Throwable>, Boolean> exceptions = new HashMap<>();
    private RetryListener retryListener;

    public static RetryTemplateBuilder builder(RetryPolicyConfiguration retryPolicyConfig) {
        return new RetryTemplateBuilder(retryPolicyConfig);
    }

    private RetryTemplateBuilder(RetryPolicyConfiguration retryPolicyConfig) {
        this.retryPolicyConfig = requireNonNull(retryPolicyConfig, "retryPolicyConfig must not be null");
    }

    /**
     * Exception for which a retry should be attempted. Subclasses and exception causes are included in checking.
     * Call multiple times to add different exceptions.
     */
    public RetryTemplateBuilder retryFor(Class<? extends Throwable> exception) {
        requireNonNull(exception, EXCEPTION_MUST_NOT_BE_NULL);
        exceptions.put(exception, true);
        return this;
    }

    /**
     * Exception for which a retry should not be attempted. Subclasses and exception causes are included in checking.
     * Call multiple times to add different exceptions.
     * <p>
     * By default, exceptions are not retried. This method can be used to change behaviour for subclasses of exceptions set by {@link #retryFor(Class)}.
     */
    public RetryTemplateBuilder dontRetryFor(Class<? extends Throwable> exception) {
        requireNonNull(exception, EXCEPTION_MUST_NOT_BE_NULL);
        exceptions.put(exception, false);
        return this;
    }

    /**
     * Callback gets called for each error (on each retry). Multiple callbacks can be set by calling this method multiple times.
     */
    public RetryTemplateBuilder retryListener(RetryListener retryListener) {
        this.retryListener = requireNonNull(retryListener, "retryListener must not be null");
        return this;
    }

    public RetryTemplate build() {
        ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
        exponentialBackOffPolicy.setInitialInterval(retryPolicyConfig.getInitialWaitMs());
        exponentialBackOffPolicy.setMaxInterval(retryPolicyConfig.getMaxWaitMs());
        exponentialBackOffPolicy.setMultiplier(retryPolicyConfig.getBackoffFactor());

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(
                this.retryPolicyConfig.getMaxAttempts(),
                new HashMap<>(exceptions),
                true);

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(exponentialBackOffPolicy);
        retryTemplate.registerListener(retryListener);
        return retryTemplate;
    }

}
