package com.dmanluc.githubrepos.domain.entity

import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty

/**
 * @author   Daniel Manrique <dmanluc91@gmail.com>
 * @version  1
 * @since    17/3/18.
 */
@Parcel(Parcel.Serialization.BEAN)
data class GithubRepo @ParcelConstructor constructor(val id: Int,
                                                     val name: String,
                                                     val fullName: String,
                                                     val ownerName: String,
                                                     val ownerAvatarUrl: String,
                                                     val ownerUrl: String,
                                                     val description: String,
                                                     @ParcelProperty("private") val isPrivate: Boolean,
                                                     val contributorsUrl: String,
                                                     var contributors: MutableList<GithubRepoContributor>?,
                                                     val createdAt: String,
                                                     val updatedAt: String,
                                                     val url: String,
                                                     val starsRating: Int,
                                                     val language: String?,
                                                     val forksNumber: Int,
                                                     val licenseName: String?)