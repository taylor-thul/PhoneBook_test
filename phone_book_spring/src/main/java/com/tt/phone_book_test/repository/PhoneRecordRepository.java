/**
 * 
 */
package com.tt.phone_book_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tt.phone_book_test.model.PhoneRecord;

public interface PhoneRecordRepository extends JpaRepository<PhoneRecord, Long> {}
