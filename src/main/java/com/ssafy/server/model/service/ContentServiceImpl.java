package com.ssafy.server.model.service;

import com.ssafy.server.model.dao.ContentMapper;
import com.ssafy.server.model.dto.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    ContentMapper contentMapper;

    @Override
    public Content getContent(int contentId) {

        Content content = contentMapper.selectContent(contentId);

        return content;
    }

    @Override
    public List<Content> getTodayContent() {
        return contentMapper.selectTodayContent();
    }

    @Override
    public List<Content> getAllContent() {
        return contentMapper.selectAllContent();
    }
}
