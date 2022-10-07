package com.uwdp.whatyouwant.Search


data class ResultGetSearchItems(
    var start: Int = 0,
    var display: Int = 0,
    var sort: String = "",
    var items: List<Items>
)

data class Items(
    var title: String = "",
    var link: String = "",
    var image: String = "",
    var lprice: Int = 0,
    var mallName: String ="",
    var brand : String = "",
    var category1 : String = "",
    var category2 : String = "",
    var category3 : String = ""
)