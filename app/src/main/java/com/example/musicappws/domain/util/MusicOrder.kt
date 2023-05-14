package com.example.musicappws.domain.util

sealed class MusicOrder(val orderType: OrderType){
    class Title(orderType: OrderType): MusicOrder(orderType)
    class Date(orderType: OrderType): MusicOrder(orderType)


    fun copy(orderType: OrderType) : MusicOrder{
        return when(this){
            is Title ->Title(orderType)
            is Date -> Date(orderType)
        }
    }
}
