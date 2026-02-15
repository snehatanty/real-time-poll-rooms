package com.sneha.realtimepoll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sneha.realtimepoll.entity.Poll;

public interface PollRepository extends JpaRepository<Poll, String> {

}
