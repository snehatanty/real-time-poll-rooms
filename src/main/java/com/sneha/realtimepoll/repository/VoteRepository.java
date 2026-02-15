package com.sneha.realtimepoll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sneha.realtimepoll.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

	boolean existsByPoll_IdAndIpAddress(String pollId, String ipAddress);
}
