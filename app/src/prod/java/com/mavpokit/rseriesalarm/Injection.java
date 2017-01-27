package com.mavpokit.rseriesalarm;

import com.mavpokit.rseriesalarm.data.source.IRepository;
import com.mavpokit.rseriesalarm.data.source.Repository;

/**
 * Created by Alex on 24.01.2017.
 */

public class Injection {
    public static IRepository provideRepository(){
        return Repository.getInstance();
    }
}
