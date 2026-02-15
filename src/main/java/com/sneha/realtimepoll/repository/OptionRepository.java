package com.sneha.realtimepoll.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sneha.realtimepoll.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {

    List<Option> findByPollId(String pollId);
}
