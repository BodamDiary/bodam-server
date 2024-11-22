package com.ssafy.server.model.service;

import com.ssafy.server.model.dto.Content;

import java.util.List;

public interface ContentService {
    Content getContent(int contentId);

    List<Content> getTodayContent();

    List<Content> getAllContent();
}
