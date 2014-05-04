package dgvolpato.SoundCloud.pi;

import java.math.BigInteger;

/**
 * Created by Diego on 01/05/2014.
 */
public class FoundLogos {
    BigInteger offset;
    Integer delta;

    public FoundLogos(BigInteger offset, Integer delta) {
        this.offset = offset;
        this.delta = delta;
    }

    public BigInteger getOffset() {
        return offset;
    }

    public Integer getDelta() {
        return delta;
    }

    public void setDelta(Integer delta) {
        this.delta = delta;
    }

    public void setOffset(BigInteger offset) {
        this.offset = offset;
    }

}
