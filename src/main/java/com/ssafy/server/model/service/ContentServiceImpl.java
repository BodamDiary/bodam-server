package com.ssafy.server.model.service;

import com.ssafy.server.model.dao.ContentMapper;
import com.ssafy.server.model.dto.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    ContentMapper contentMapper;

    @Override
    public Content getContent(int contentId) {

        Content content = contentMapper.selectContent(contentId);

        return content;
    }
}
