package model;

import lombok.Data;

/**
 * Created by saipkri on 28/12/16.
 */
@Data
public class Task {
    private final String name;
    private final long startedTimestamp = System.currentTimeMillis();
}
