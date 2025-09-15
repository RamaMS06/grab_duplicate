package com.example.grabduplicates.ui.screen.home

import androidx.lifecycle.ViewModel
import com.example.grabduplicates.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class MenuItem(
    val image: Int,
    val title: String,
)

class HomeViewModel : ViewModel() {

    val listOfMenuItem = listOf<MenuItem>(
        MenuItem(R.drawable.img_food, "Home"),
        MenuItem(R.drawable.img_mart, "Mart"),
        MenuItem(R.drawable.img_express, "Express"),
        MenuItem(R.drawable.img_car, "Transport"),
        MenuItem(R.drawable.img_market, "Market"),
        MenuItem(R.drawable.img_offers, "Offers"),
        MenuItem(R.drawable.img_gift, "Gift Cards"),
        MenuItem(R.drawable.img_more, "More"),
    )

    val listOfSuggestionsSearch = listOf<String>(
        "Search coffee shops",
        "Starbucks near me",
        "Best sushi in town",
        "24/7 pharmacy",
        "Dimsum near me",
        "Bubble tea nearby",
        "Chicken rice delivery",
        "Best pizza deals",
        "Late night burgers",
        "Bakso near me",
        "Ayam geprek delivery",
        "Healthy salad options",
        "Hotpot restaurants",
        "Ramen places near me",
        "Martabak delivery",
        "Ice cream nearby",
        "Seafood BBQ",
        "Vegan food options",
        "Groceries in 15 minutes",
        "Discounted meals today"
    )


    private val _searchValue: MutableStateFlow<String> = MutableStateFlow("")
    val searchValue: StateFlow<String> = _searchValue

    fun setSearchValue(value: String) {
        _searchValue.value = value
    }

}