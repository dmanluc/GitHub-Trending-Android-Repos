package com.dmanluc.githubrepos.domain.entity

import org.parceler.Parcel
import org.parceler.ParcelConstructor

/**
 * @author   Daniel Manrique <dmanluc91@gmail.com>
 * @version  1
 * @since    17/3/18.
 */
@Parcel(Parcel.Serialization.BEAN)
data class GithubRepoContributor @ParcelConstructor constructor(val id: Int,
                                                                val loginName: String,
                                                                val avatarUrl: String,
                                                                val url: String)