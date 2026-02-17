package com.sneha.realtimepoll.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sneha.realtimepoll.dto.CreatePollRequest;
import com.sneha.realtimepoll.dto.VoteRequest;
import com.sneha.realtimepoll.entity.Option;
import com.sneha.realtimepoll.entity.Poll;
import com.sneha.realtimepoll.entity.Vote;
import com.sneha.realtimepoll.repository.OptionRepository;
import com.sneha.realtimepoll.repository.PollRepository;
import com.sneha.realtimepoll.repository.VoteRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import java.util.UUID;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private OptionRepository optionRepository;
    
    @Autowired
    private VoteRepository voteRepository;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;




    @PostMapping
    public ResponseEntity<?> createPoll(@RequestBody CreatePollRequest request) {

        // Validation: minimum 2 options
        if (request.getOptions() == null || request.getOptions().size() < 2) {
            return ResponseEntity.badRequest().body("A poll must have at least 2 options.");
        }

        // Create poll
        Poll poll = new Poll();
        poll.setQuestion(request.getQuestion());
        poll.setCreatedAt(LocalDateTime.now());
        poll.setExpiresAt(request.getExpiresAt());

        pollRepository.save(poll);

        // Save options
        for (String optionText : request.getOptions()) {
            Option option = new Option();
            option.setText(optionText);
            option.setPoll(poll);
            optionRepository.save(option);
        }

        // Return poll ID (shareable link ID)
        return ResponseEntity.ok(poll.getId());
    }
    @GetMapping("/{pollId}")
    public ResponseEntity<?> getPoll(@PathVariable String pollId) {

        return pollRepository.findById(pollId)
                .map(poll -> {

                    var options = optionRepository.findByPollId(pollId)
                            .stream()
                            .map(opt -> new com.sneha.realtimepoll.dto.OptionResponse(
                                    opt.getId(),
                                    opt.getText(),
                                    opt.getVoteCount()))
                            .toList();

                    var response = new com.sneha.realtimepoll.dto.PollResponse(
                            poll.getId(),
                            poll.getQuestion(),
                            poll.getCreatedAt(),
                            poll.getExpiresAt(),
                            options
                    );

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/{pollId}/vote")
    public ResponseEntity<?> vote(
            @PathVariable String pollId,
            @RequestBody VoteRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {

        String ip = httpRequest.getRemoteAddr();
        
     // ✅ Anti-abuse #2: one vote per browser using clientId cookie
        String clientId = null;

        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("clientId".equals(c.getName())) {
                    clientId = c.getValue();
                    break;
                }
            }
        }

        // If cookie missing, create new clientId
        if (clientId == null || clientId.isBlank()) {
            clientId = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("clientId", clientId);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 365); // 1 year
            cookie.setHttpOnly(false);
            httpResponse.addCookie(cookie);
        }


        // Check duplicate vote
        if (voteRepository.existsByPoll_IdAndIpAddress(pollId, ip)) {
            return ResponseEntity.badRequest()
                    .body("You have already voted in this poll");
        }
        
        if (voteRepository.existsByPoll_IdAndSessionId(pollId, clientId)) {
            return ResponseEntity.badRequest()
                    .body("You have already voted in this poll (browser)");
        }


        var option = optionRepository.findById(request.getOptionId());

        if (option.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid option");
        }

        var selectedOption = option.get();
        
        Poll poll = selectedOption.getPoll();

     // 🔥 Expiry Fairness Mechanism
     if (poll.getExpiresAt() != null &&
         poll.getExpiresAt().isBefore(LocalDateTime.now())) {
         return ResponseEntity.badRequest()
                 .body("Poll has expired");
     }


        if (!selectedOption.getPoll().getId().equals(pollId)) {
            return ResponseEntity.badRequest()
                    .body("Option does not belong to this poll");
        }

        // Increase vote count
        selectedOption.setVoteCount(selectedOption.getVoteCount() + 1);
        optionRepository.save(selectedOption);

        // Save vote record
        Vote vote = new Vote();
        vote.setSessionId(clientId);
        vote.setIpAddress(ip);
        vote.setVotedAt(LocalDateTime.now());
        vote.setPoll(selectedOption.getPoll());
        vote.setOption(selectedOption);
        

        voteRepository.save(vote);
        
        messagingTemplate.convertAndSend(
                "/topic/polls/" + pollId,
                "updated"
        );

        return ResponseEntity.ok("Vote recorded successfully");
    }




}
