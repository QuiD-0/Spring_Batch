package com.quid.batch.job.coupon

import org.springframework.batch.item.ItemReader

class CustomItemReader(
        private val list : ArrayList<Long>
): ItemReader<Long>{


    override fun read(): Long? {
        if(list.isNotEmpty()){
            return list.removeAt(0)
        }
        return null
    }
}