package org.timer;

import java.time.Duration;
import java.time.Instant;

public class TimeWatch {

    private Instant start;
    private Instant end;

    public void startTimer(){
        start = Instant.now();
    }

    public double stopTimer(){

        end = Instant.now();

        return Duration.between(start, end).toMillis();

    }

}
