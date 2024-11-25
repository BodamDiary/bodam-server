package com.ssafy.server.model.service;

import com.ssafy.server.model.dao.BodamMapper;
import com.ssafy.server.model.dto.Bodam;
import com.ssafy.server.model.dto.Family;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BodamServiceImpl implements BodamService{

    @Autowired
    BodamMapper bodamMapper;

    @Autowired
    StringToLocalDateConverter stringToLocalDateConverter;

    @Override
    public int registBodam(Bodam bodam, Family family) {
        System.out.println("bodam 이거 나와야함 :: " + bodam.toString());
        LocalDate birthday = stringToLocalDateConverter.convert(bodam.getStringBirthday());
        System.out.println("convert 된 birthday::" + birthday.toString());
        bodam.setBirthday(birthday);
        System.out.println("set birthday 함 :: " + bodam.toString());
        System.out.println("birthday의 타입" + (bodam.getBirthday().getClass()));
        int isRegistered = bodamMapper.insertBodam(bodam);
        System.out.println("가장중요한 보담아이디 : " + bodam.getBodamId());
        System.out.println("insert bodam은 됐는데...! ::: " + isRegistered);
        family.setBodamId(bodam.getBodamId());
        System.out.println("family.getBodamId ::::::::: " + family.getBodamId());
        int isFamilyRegistered = bodamMapper.insertFamily(family);
        System.out.println("service의 isRegistered = " + isRegistered);
        System.out.println("service의 isFamilyRegistered = " + isFamilyRegistered);
        return isRegistered;
    }

    @Override
    public Bodam getBodamByUser(int userId) {
        Integer bodamId = bodamMapper.selectBodamIdByUser(userId);
        System.out.println("bodamId = 이거 null 이니?" + bodamId);
        if (bodamId == null) {
            return null;
        }
        Bodam bodam = bodamMapper.selectBodamByUser(bodamId);
        System.out.println("bodam = " + bodam);
        return bodam;
    }

    @Override
    public int modifyBodam(int bodamId) {
        int isUpdated = bodamMapper.updateBodam(bodamId);
        return isUpdated;
    }


    @Override
    public int getUserIdByBodam(int bodamId) {
        int userId = bodamMapper.selectUserIdByBodamId(bodamId);
        return userId;
    }


}
