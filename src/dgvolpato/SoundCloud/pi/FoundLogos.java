package dgvolpato.SoundCloud.pi;

import java.math.BigInteger;

/**
 * Created by Diego on 01/05/2014.
 */
public class FoundLogos {
    long offset;
    Integer delta;

    public FoundLogos(long offset, Integer delta) {
        this.offset = offset;
        this.delta = delta;
    }

    public long getOffset() {
        return offset;
    }

    public Integer getDelta() {
        return delta;
    }

    public void setDelta(Integer delta) {
        this.delta = delta;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

}
