package net.riser876.stacknator.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Config {

    @Expose
    @SerializedName("enabled")
    public boolean ENABLED = true;

    @Expose
    @SerializedName("log_modified_items")
    public boolean LOG_MODIFIED_ITEMS = true;

    @Expose
    @SerializedName("checks")
    public CheckConfig CHECKS = new CheckConfig();

    @Expose
    @SerializedName("remove_defaults")
    public boolean REMOVE_DEFAULTS = false;

    @Expose
    @SerializedName("filler")
    public FillerConfig FILLER = new FillerConfig();

    @Expose
    @SerializedName("sort_items")
    public boolean SORT_ITEMS = true;

    @Expose
    @SerializedName("items")
    public Map<String, Integer> ITEMS = new HashMap<>();

    public static class CheckConfig {

        @Expose
        @SerializedName("enable_checks")
        public boolean CHECKS_ENABLED = true;

        @Expose
        @SerializedName("check_damageable")
        public boolean CHECK_DAMAGEABLE = true;

        @Expose
        @SerializedName("check_stackable")
        public boolean CHECK_STACKABLE = false;

        @Expose
        @SerializedName("check_minimum_stack_size")
        public boolean CHECK_MINIMUM_STACK_SIZE = true;

        @Expose
        @SerializedName("check_maximum_stack_size")
        public boolean CHECK_MAXIMUM_STACK_SIZE = true;
    }

    public static class FillerConfig {

        @Expose
        @SerializedName("enable_filler")
        public boolean FILLER_ENABLED = false;

        @Expose
        @SerializedName("reset_stacks")
        public boolean RESET_STACKS = false;

        @Expose
        @SerializedName("run_once")
        public boolean RUN_ONCE = true;

        @Expose
        @SerializedName("tag_priority")
        public boolean TAG_PRIORITY = true;

        @Expose
        @SerializedName("tags")
        public Map<String, Integer> TAGS = new HashMap<>();
    }
}