package com.ssafy.server.model.service;

import com.ssafy.server.model.dao.BodamMapper;
import com.ssafy.server.model.dto.Bodam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BodamServiceImpl implements BodamService{

    @Autowired
    BodamMapper bodamMapper;

    @Override
    public int registBodam(Bodam bodam) {

        int isRegistered = bodamMapper.insertBodam(bodam);

        return isRegistered;
    }
}
