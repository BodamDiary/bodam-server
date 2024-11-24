package com.ssafy.server.model.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PdfGeneratorService {

    private static final String PDF_DIRECTORY = "http://localhost:8080/";

    public String generatePdf(String analysisResult) {
        try {
            // PDF 파일명 생성
            String fileName = "analysis-result-" + System.currentTimeMillis() + ".pdf";
            String filePath = PDF_DIRECTORY + fileName;

            // PDF 생성
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // 분석 결과 추가
            document.add(new Paragraph("Diary Analysis Result:"));
            document.add(new Paragraph(analysisResult));
            document.close();

            // PDF 파일 URL 반환
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}
