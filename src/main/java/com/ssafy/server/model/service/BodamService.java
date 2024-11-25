package com.ssafy.server.model.service;

import com.ssafy.server.model.dto.Bodam;
import com.ssafy.server.model.dto.Family;

import java.util.List;

public interface BodamService {

    int registBodam(Bodam bodam, Family family);

    Bodam getBodamByUser(int userId);

    int modifyBodam(int bodamId);


    int getUserIdByBodam(int bodamId);
}
