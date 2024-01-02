package jp.co.sample.configurations;

import io.vertx.core.json.JsonObject;

public class SessionOptions {

    private static final String DEFAULT_PATH = "/";
    private static final int DEFAULT_MAX_AGE = 60;
    private static final int DEFAULT_TIME_OUT = 30 * 60 * 1000;

    private final JsonObject plain;
    private final String path;
    private final int maxAge;
    private final int timeout;

    public SessionOptions() {
        this.plain = null;
        this.path = DEFAULT_PATH;
        this.maxAge = DEFAULT_MAX_AGE;
        this.timeout = DEFAULT_TIME_OUT;
    }

    public SessionOptions(JsonObject sessionOptions) {
        this.plain = sessionOptions;

        if (sessionOptions.containsKey("path")) {
            this.path = sessionOptions.getString("path", DEFAULT_PATH);
        } else
            this.path = DEFAULT_PATH;

        if (sessionOptions.containsKey("max-age")) {
            this.maxAge = sessionOptions.getInteger("max-age", DEFAULT_MAX_AGE);
        } else
            this.maxAge = DEFAULT_MAX_AGE;

        if (sessionOptions.containsKey("timeout")) {
            this.timeout = sessionOptions.getInteger("timeout", DEFAULT_TIME_OUT);
        } else
            this.timeout = DEFAULT_TIME_OUT;
    }

    public String path() {
        return this.path;
    }

    public int maxAge() {
        return this.maxAge;
    }

    public int timeout() {
        return this.timeout;
    }

    public JsonObject toJsonObject() {
        return this.plain;
    }

    public boolean isEmpty() {
        return this.plain == null;
    }
}
