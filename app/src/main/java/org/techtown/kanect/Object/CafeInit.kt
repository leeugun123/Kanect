package org.techtown.kanect.Object

import org.techtown.kanect.Data.CafeIntel

class CafeInit {
        companion object {
            val cafeList = listOf(
                CafeIntel(
                    "https://firebasestorage.googleapis.com/v0/b/kanect-ced83.appspot.com/o/starbucks_logo.PNG?alt=media&token=d2a0d513-dacd-4862-8e7e-ec2decc5d91e",
                    "스타벅스(공릉점)",
                    30,
                    false,
                    GetTime.opExist(730, 2200),
                    cur_seat = 0,
                    operTime = "07:30 ~ 22:00",
                    plugSeat = 20
                ),
                CafeIntel(
                    "https://firebasestorage.googleapis.com/v0/b/kanect-ced83.appspot.com/o/twosomeplace_logo.PNG?alt=media&token=89756cde-060f-47f5-a69b-227f80d534b7",
                    "투썸플레이스(공릉점)",
                    30,
                    false,
                    GetTime.opExist(900, 2400),
                    cur_seat = 0,
                    operTime = "09:00 ~ 24:00",
                    plugSeat = 10
                ),
                CafeIntel(
                    "https://firebasestorage.googleapis.com/v0/b/kanect-ced83.appspot.com/o/tomtom_logo.PNG?alt=media&token=4b68af13-cd9c-4230-8a0d-5c8460437ee6",
                    "탐탐(공릉점)",
                    30,
                    true,
                    GetTime.opExist(0, 2400),
                    cur_seat = 0,
                    operTime = "00:00 ~ 24:00",
                    plugSeat = 30
                )
            )
        }

        // 나머지 클래스 내용...




}