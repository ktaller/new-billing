package com.billing_ktaller.water_billing.controllers;

import com.billing_ktaller.water_billing.models.Comments;
import com.billing_ktaller.water_billing.repositories.CommentRepository;
import com.billing_ktaller.water_billing.repositories.MeterRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MeterRepository meterRepository;

    // POST method to create a new comment
    @PostMapping("/create")
    public ResponseEntity<String> createComment(@Valid @RequestBody Comments comment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the meter exists before saving
        if (!meterRepository.existsById(comment.getMeter().getId())) {
            return ResponseEntity.badRequest().body("Meter does not exist.");
        }

        commentRepository.save(comment);
        return ResponseEntity.ok("Comment created successfully");
    }

    // POST method to update a comment
    @PostMapping("/update")
    public ResponseEntity<String> updateComment(@Valid @RequestBody Comments comment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the comment exists before updating
        if (!commentRepository.existsById(comment.getId())) {
            return ResponseEntity.badRequest().body("Comment does not exist.");
        }

        commentRepository.save(comment);
        return ResponseEntity.ok("Comment updated successfully");
    }

    // POST method to delete a comment
    @PostMapping("/delete")
    public ResponseEntity<String> deleteComment(@Valid @RequestBody Comments comment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the comment exists before deleting
        if (!commentRepository.existsById(comment.getId())) {
            return ResponseEntity.badRequest().body("Comment does not exist.");
        }

        commentRepository.delete(comment);
        return ResponseEntity.ok("Comment deleted successfully");
    }

    // GET method to retrieve all comments
    @PostMapping("")
    public ResponseEntity<List<Comments>> getComments() {
        return ResponseEntity.ok(commentRepository.findAll());
    }
}
