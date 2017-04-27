package pw.koj.jetstreem.compiler.ir;

import java.time.ZonedDateTime;

public class TimeConstant {
    private ZonedDateTime value;

    public TimeConstant(ZonedDateTime value) {
        this.value = value;
    }
}

