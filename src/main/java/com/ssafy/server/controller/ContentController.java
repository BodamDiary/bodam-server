package com.ssafy.server.controller;

import com.ssafy.server.model.dto.Content;
import com.ssafy.server.model.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping("/get-content/{contentId}")
    public ResponseEntity<Content> getContent(@PathVariable int contentId) {
        System.out.println("getContent");
        Content content = contentService.getContent(contentId);
        System.out.println(content);

        if (content != null) {
            return ResponseEntity.ok().body(content);
        }

        return ResponseEntity.notFound().build();
    }
}
