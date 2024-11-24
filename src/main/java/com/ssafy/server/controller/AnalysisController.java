package com.ssafy.server.controller;

import com.ssafy.server.model.service.DiaryAnalysisService;
import com.ssafy.server.model.service.PdfGeneratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AnalysisController {

    private final DiaryAnalysisService diaryAnalysisService;
    private final PdfGeneratorService pdfGeneratorService;

    public AnalysisController(DiaryAnalysisService diaryAnalysisService, PdfGeneratorService pdfGeneratorService) {
        this.diaryAnalysisService = diaryAnalysisService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @GetMapping("/analyze-diaries")
    public ResponseEntity<?> analyzeDiaries(Authentication authentication) {
        int userId = Integer.parseInt(authentication.getName());
        try {
            String analysisResult = diaryAnalysisService.analyzeDiaries(userId);
            String pdfUrl = pdfGeneratorService.generatePdf(analysisResult);
            return ResponseEntity.ok(Map.of("pdfUrl", pdfUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
