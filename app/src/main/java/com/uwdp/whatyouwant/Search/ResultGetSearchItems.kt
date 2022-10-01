package com.uwdp.whatyouwant.Search

data class ResultGetSearchItems(
    var start: Int = 0,
    var display: Int = 0,
    var items: List<Items>
)

data class Items(
    var title: String = "",
    var link: String = "",
    var image: String = "",
    var lprice: Int = 0
)