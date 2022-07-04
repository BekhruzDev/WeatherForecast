package com.bekhruz.weatherforecast.data.remote.utils

interface ListMapper<I, O>:Mapper<I, O> {
   fun asDomainList(dtoList:List<I>):List<O>{
       return dtoList.map { asDomain(it) }
   }
}