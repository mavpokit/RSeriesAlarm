package com.mavpokit.rseriesalarm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mavpokit.rseriesalarm.data.source.IRepository;
import com.mavpokit.rseriesalarm.data.source.Repository;
import com.mavpokit.rseriesalarm.data.source.sqlite.SqliteDatasource;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by Alex on 24.01.2017.
 */

public class Injection {
    public static IRepository provideRepository(@NonNull Context context){
        return Repository.getInstance(SqliteDatasource.getInstance(checkNotNull(context)));
    }
}
