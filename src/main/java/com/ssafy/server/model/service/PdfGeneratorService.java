package com.ssafy.server.model.service;

import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfGeneratorService {

    public void generatePdf(String analysisResult, HttpServletResponse response) {
        try {
            // 메모리에서 PDF 생성
            System.out.println("generate pdf까지 들어옴");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            String fontPath = "classpath:static/font/Pretendard-Bold.ttf";
            PdfFont BoldFont = PdfFontFactory.createFont(FontProgramFactory.createFont(fontPath), "Identity-H");
            System.out.println("font 설정 완료 :: " + BoldFont + " ||| " + "font path :" + fontPath);
            Paragraph paragraph = new Paragraph("보담 발달 보고서")
                    .setFont(BoldFont)
                    .setFontSize(20);

            document.add(paragraph);
            System.out.println("document.add(paragraph) 성공~");

            // 한글 폰트 설정
            // Spring에서 classpath 경로로 리소스 읽기
            fontPath = "classpath:static/font/Pretendard-Regular.ttf";
            System.out.println("Spring에서 classpath 경로로 리소스 읽기 1");
            PdfFont koreanFont = PdfFontFactory.createFont(FontProgramFactory.createFont(fontPath), "Identity-H");
            System.out.println("Spring에서 classpath 경로로 리소스 읽기 2");
            document.setFont(koreanFont);
            System.out.println("Spring에서 classpath 경로로 리소스 읽기 3");

            // PDF에 분석 결과 추가
            document.add(new Paragraph(analysisResult));
            document.close();
            System.out.println("분석 결과 추가");

            // HTTP 응답 설정
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=analysis-result.pdf");
            response.getOutputStream().write(byteArrayOutputStream.toByteArray());
            response.getOutputStream().flush();
            System.out.println("응답 설정 완료");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}
