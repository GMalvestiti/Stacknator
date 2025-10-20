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
    @SerializedName("sorter")
    public SorterConfig SORTER = new SorterConfig();

    @Expose
    @SerializedName("tags")
    public Map<String, Integer> TAGS = new HashMap<>();

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
        @SerializedName("check_maximum_count_one")
        public boolean CHECK_MAXIMUM_COUNT_ONE = false;

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
        @SerializedName("filter_damageable")
        public boolean FILTER_DAMAGEABLE = true;

        @Expose
        @SerializedName("filter_unstackable")
        public boolean FILTER_UNSTACKABLE = false;

        @Expose
        @SerializedName("filter_maximum_count_one")
        public boolean FILTER_MAXIMUM_COUNT_ONE = false;

        @Expose
        @SerializedName("reset_stacks")
        public boolean RESET_STACKS = false;

        @Expose
        @SerializedName("run_once")
        public boolean RUN_ONCE = true;
    }

    public static class SorterConfig {

        @Expose
        @SerializedName("sorter_enabled")
        public boolean SORTER_ENABLED = false;

        @Expose
        @SerializedName("sort_tags")
        public boolean SORT_TAGS = false;

        @Expose
        @SerializedName("sort_items")
        public boolean SORT_ITEMS = false;
    }
}
