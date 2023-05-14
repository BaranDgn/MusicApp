package com.example.musicappws.domain.util

sealed class OrderType{
        object Ascending: OrderType()
        object Descending: OrderType()
}
