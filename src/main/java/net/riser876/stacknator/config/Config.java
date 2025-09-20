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
    @SerializedName("log_success")
    public boolean LOG_SUCCESS = true;

    @Expose
    @SerializedName("stacks")
    public Map<String, Integer> STACKS = new HashMap<>();
}
