package com.example.chatapp.model

import com.example.chatapp.R

data class SpinnerCategory(
    val id: String? = null,
    val name: String? = null,
    val imageId: Int = 0,
) {
    companion object {
        const val MUSIC = "Music"
        const val SPORTS = "Sports"
        const val MOVIES = "Movies"

        fun fromIdToCat(catId: String): SpinnerCategory {
            when (catId) {
                MUSIC -> {
                    return SpinnerCategory(
                        MUSIC,
                        MUSIC,
                        R.drawable.music,
                    )
                }
                SPORTS -> {
                    return SpinnerCategory(
                        SPORTS,
                        SPORTS,
                        R.drawable.sport,
                    )
                }
                else -> {
                    return SpinnerCategory(
                        MOVIES,
                        MOVIES,
                        R.drawable.movies,
                    )
                }
            }
        }

        fun getSpinnerCategoriesList(): List<SpinnerCategory> {
            return listOf(fromIdToCat(MUSIC), fromIdToCat(SPORTS), fromIdToCat(MOVIES))
        }
    }
}