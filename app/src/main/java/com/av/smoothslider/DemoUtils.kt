package com.av.smoothslider

import com.av.smoothviewpager.smoolider.PageModel

private val titles = intArrayOf(R.string.title_1, R.string.title_2, R.string.title_3, R.string.title_4, R.string.title_5)
private val descriptions = intArrayOf(
    R.string.description_1, R.string.description_2, R.string.description_3, R.string.description_4,
    R.string.description_5
)
private val pics = intArrayOf(R.drawable.img01, R.drawable.img02, R.drawable.img03, R.drawable.img04, R.drawable.img05)
private val url = intArrayOf(R.string.position_1, R.string.position_2, R.string.position_3, R.string.position_4, R.string.position_5)
private val chipTexts = arrayOf("S 65", "400 4Matic", "GT 63S", "G 63", "C 63S")

fun getDemoData(): List<PageModel> {
    val pagesList: MutableList<PageModel> = mutableListOf()
    pics.forEachIndexed { index, image ->
        val dataModel = DemoDataModel(titles[index], descriptions[index], url[index])
        pagesList.add(
            PageModel(
                imageResource = image,
                descriptionChipText = chipTexts[index],
                data = dataModel
            )
        )
    }
    return pagesList
}

fun getSinglePage(): PageModel {
    val index = (0..4).random()
    val dataModel = DemoDataModel(titles[index], descriptions[index], url[index])
    return PageModel(
        imageResource = pics[index],
        descriptionChipText = chipTexts[index],
        data = dataModel
    )
}
