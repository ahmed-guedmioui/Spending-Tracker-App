package com.ahmed_apps.soufian_spending_tracker.spending_details.data

import com.ahmed_apps.soufian_spending_tracker.core.data.local.SpendingEntity
import com.ahmed_apps.soufian_spending_tracker.core.data.local.SpendingDatabase
import com.ahmed_apps.soufian_spending_tracker.core.data.local.SpendingDao
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.koin.test.KoinTest
import org.koin.test.inject
import java.time.ZonedDateTime
import kotlin.test.Test

/**
 * @author Ahmed Guedmioui
 */
class SpendingDaoTest : KoinTest {

    private val spendingDao: SpendingDao by inject()
    private val spendingDatabase: SpendingDatabase by inject()

    @After
    fun tearDown() {
        spendingDatabase.close()
    }

    @Test
    fun getAllCategoriesFromEmptyDb_categoryListIsEmpty() = runTest {
        Truth.assertThat(
            spendingDao.getAllDates()
        ).isEmpty()
    }

    @Test
    fun getAllCategoriesFromDb_CategoriesListIsNotEmpty() = runTest {
        for (i in 1..4) {
            val spendingEntity = SpendingEntity(
                id = null,
                name = "Category $i",
                imageUrl = "image $i",
                price = i.toDouble(),
                dateTimeUtc = ZonedDateTime.now().toInstant().toString()
            )

            spendingDao.upsertSpending(spendingEntity)
        }

        Truth.assertThat(
            spendingDao.getAllCategories()
        ).isNotEmpty()
    }

    @Test
    fun upsertCategory_spendingIsUpserted() = runTest {
        val spendingEntity = SpendingEntity(
            id = 1,
            name = "Category",
            imageUrl = "image",
            price = 1.3,
            dateTimeUtc = ZonedDateTime.now().toInstant().toString()
        )

        spendingDao.upsertSpending(spendingEntity)

        Truth.assertThat(
            spendingDao.getAllCategories()
        ).contains(spendingEntity)
    }

    @Test
    fun deleteCategory_spendingIsDeleted() = runTest {
        val spendingEntity = SpendingEntity(
            id = 1,
            name = "Category",
            imageUrl = "image",
            price = 1.3,
            dateTimeUtc = ZonedDateTime.now().toInstant().toString()
        )

        spendingDao.upsertSpending(spendingEntity)

        Truth.assertThat(
            spendingDao.getAllCategories()
        ).contains(spendingEntity)

        spendingDao.deleteSpending(spendingEntity.id ?: 1)

        Truth.assertThat(
            spendingDao.getAllCategories()
        ).doesNotContain(spendingEntity)
    }

}

















