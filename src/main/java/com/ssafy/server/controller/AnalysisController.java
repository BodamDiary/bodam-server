package com.ssafy.server.controller;

import com.ssafy.server.model.service.DiaryAnalysisService;
import com.ssafy.server.model.service.PdfGeneratorService;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<String> analyzeDiaries(Authentication authentication, HttpServletResponse response) {
        int userId = Integer.parseInt(authentication.getName());
        try {
            String analysisResult = diaryAnalysisService.analyzeDiaries(userId);
            System.out.println("analyzeDiaries:"+analysisResult);
            pdfGeneratorService.generatePdf(analysisResult, response);
            return ResponseEntity.ok("pdf generated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
