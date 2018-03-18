package com.dmanluc.githubrepos.data.mapper

/**
 * Transformer interface to adapt an specific output contract from JSON response to the specific domain entity of the app
 *
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    18/3/18.
 */
interface Mapper<in R, out T> {

    fun mapApiContractToDomainModel(outputContract: R): List<T>

}