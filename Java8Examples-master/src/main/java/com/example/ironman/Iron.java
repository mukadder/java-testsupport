package com.example.ironman;

/**
 * Created by MHP on 24/07/2015.
 */
public enum Iron {
    SUPER_SAIYAN,
    SUPER_SAIYA_2,
    ADAMANTIUM,
    SUPER_SAIYA_GOD,
    GOLDER_GREAT_APE;


    public Double getPower() {
        if (this == ADAMANTIUM)
            return 100.0;
        return 0.0;
    }
}
