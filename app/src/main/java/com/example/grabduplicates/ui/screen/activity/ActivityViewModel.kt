package com.example.grabduplicates.ui.screen.activity

import androidx.lifecycle.ViewModel
import com.example.grabduplicates.R

data class HeaderActivityItem(
    val title: String,
    val icon: Int,
    val desc: String? = null,
    val link: String? = null,
)

data class HeaderRecentItem(
    val title: String,
    val icon: Int? = null,
    val date: String,
    val isCancel: Boolean = false,
    val total: String? = null,
    val points: String? = null,
    val rebook: Boolean? = false
)

class ActivityViewModel : ViewModel() {
    val listOfHeaderDataActivity = listOf<HeaderActivityItem>(
        HeaderActivityItem(
            title = "You have 12 gift points now",
            icon = R.drawable.img_gift,
            desc = "Use them to redeem exclusive items!",
            link = "Explore GrabRewards"
        ),
        HeaderActivityItem(
            title = "Thanks for riding with us!",
            icon = R.drawable.img_crown,
            desc = "You took 14 rides with us in the last 30days.",
            link = "Save on your next few rides"
        ),
    )

    val listOfRecentDataActivity = listOf<HeaderRecentItem>(
        HeaderRecentItem(
            title = "Jakarta Selatan, Cilandak",
            date = "2 Oct 2025, 15:14",
            isCancel = false,
            total = "Rp125.500",
            points = "+177 points",
            rebook = true
        ),
        HeaderRecentItem(
            title = "Kencana Residence to Bei Cafe Jendral Sudirman",
            date = "2 Oct 2025, 15:02",
            isCancel = true,
            rebook = false
        ),
        HeaderRecentItem(
            title = "Kencana Residence to Bei Cafe Jendral Sudirman",
            date = "2 Oct 2025, 15:01",
            isCancel = true,
            rebook = false
        ),
        HeaderRecentItem(
            title = "Kencana Residence to Bei Cafe Jendral Sudirman",
            date = "2 Oct 2025, 15:00",
            isCancel = true,
            rebook = false
        ),
        HeaderRecentItem(
            title = "Kencana Residence to Bei Cafe Jendral Sudirman",
            date = "2 Oct 2025, 14:20",
            isCancel = true,
            rebook = false
        )
    )
}