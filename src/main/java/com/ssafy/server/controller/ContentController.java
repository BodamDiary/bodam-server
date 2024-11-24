package com.ssafy.server.controller;

import com.ssafy.server.model.dto.Content;
import com.ssafy.server.model.service.ContentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
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

    @GetMapping("/get-today-content")
    public ResponseEntity<List<Content>> getTodayContent(HttpServletRequest request) {
        log.info("오늘의 콘텐츠 가져오기");
        List<Content> list = contentService.getTodayContent();
        HttpSession session = request.getSession(false);
        log.info("세션아이디" + session.getId());
        if (list == null || list.isEmpty()) {
            log.info("콘텐츠 없음");
            return ResponseEntity.internalServerError().build();
        }
        log.info("콘텐츠 불러오기 성공");
        return ResponseEntity.ok(list);
    }

    @GetMapping("/get-all-content")
    public ResponseEntity<List<Content>> getAllContent() {
        log.info("모든 콘텐츠 가져오기");
        List<Content> list = contentService.getAllContent();

        if (list == null || list.isEmpty()) {
            log.info("콘텐츠 없음");
            return ResponseEntity.internalServerError().build();
        }
        log.info("콘텐츠 불러오기 성공");
        return ResponseEntity.ok(list);
    }}
