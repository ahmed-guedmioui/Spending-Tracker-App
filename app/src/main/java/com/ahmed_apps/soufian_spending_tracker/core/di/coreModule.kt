package com.ahmed_apps.soufian_spending_tracker.core.di

import android.content.Context
import androidx.room.Room
import com.ahmed_apps.soufian_spending_tracker.core.data.CoreDataSourceImpl
import com.ahmed_apps.soufian_spending_tracker.core.data.RoomSpendingDataSource
import com.ahmed_apps.soufian_spending_tracker.core.data.TestingSpendingDataSource
import com.ahmed_apps.soufian_spending_tracker.core.data.local.SpendingDatabase
import com.ahmed_apps.soufian_spending_tracker.core.domain.CoreDataSource
import com.ahmed_apps.soufian_spending_tracker.core.domain.LocalSpendingDataSource
import com.ahmed_apps.soufian_spending_tracker.spending_details.data.RetrofitImagesDataSource
import com.ahmed_apps.soufian_spending_tracker.spending_details.data.remote.api.ImagesApi
import com.ahmed_apps.soufian_spending_tracker.spending_details.domian.RemoteImagesDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Ahmed Guedmioui
 */

val coreModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            SpendingDatabase::class.java,
            "soufian_spending_tracker.db"
        ).build()
    }

    single {
        androidApplication().getSharedPreferences(
            "soufian_spending_tracker", Context.MODE_PRIVATE
        )
    }

    singleOf(::CoreDataSourceImpl).bind<CoreDataSource>()

    single { get<SpendingDatabase>().spendingDao }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ImagesApi.BASE_URL)
            .build()
            .create(ImagesApi::class.java)
    }

    singleOf(::RoomSpendingDataSource).bind<LocalSpendingDataSource>()
    singleOf(::RetrofitImagesDataSource).bind<RemoteImagesDataSource>()


}














