package app.model;

import java.math.BigDecimal;

/**
 * Created by mloda on 2016-08-15.
 */
public class Math {

    public double round4(double number){
        BigDecimal bd = BigDecimal.valueOf(number);
        bd = bd.setScale(4, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
}
