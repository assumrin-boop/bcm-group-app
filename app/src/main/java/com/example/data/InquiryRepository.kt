package com.example.data

import kotlinx.coroutines.flow.Flow

class InquiryRepository(private val inquiryDao: InquiryDao) {
    val allInquiries: Flow<List<InquiryEntity>> = inquiryDao.getAllInquiries()

    suspend fun insertInquiry(inquiry: InquiryEntity) {
        inquiryDao.insertInquiry(inquiry)
    }

    suspend fun deleteInquiry(id: Int) {
        inquiryDao.deleteInquiryById(id)
    }
}
