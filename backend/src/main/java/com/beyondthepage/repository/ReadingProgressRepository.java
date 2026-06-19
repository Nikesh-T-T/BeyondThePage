package com.beyondthepage.repository;

import com.beyondthepage.entity.ReadingProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingProgressRepository extends JpaRepository<ReadingProgress, String> {
}
