package org.techtown.kanect

import org.junit.Test

import org.junit.Assert.*
import org.techtown.kanect.Object.GetCafeNum
import org.techtown.kanect.ViewModel.CafeReviewViewModel

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `리뷰 작성 테스트`() {

        val cafeReviewViewModel = CafeReviewViewModel()

        cafeReviewViewModel.getCafeReviewData("탐탐(공릉점)")
4
        val posts = cafeReviewViewModel.cafeReviewLiveData

        assert(posts?.value?.size == 1)
        assert(posts?.value?.get(0)?.userName == "이유건")
        assert(posts?.value?.get(0)?.userReview == "너무 좋습니다.")

    }

    @Test
    suspend fun `카페 인원 테스트`() {

        val getCafeNum = GetCafeNum

        assert(getCafeNum.getCafeNum("스타벅스(공릉점)") == 23)
        assert(getCafeNum.getCafeNum("탐탐(공릉점)") == 23)
        assert(getCafeNum.getCafeNum("투썸플레이스(공릉점)") == 23)

    }

}