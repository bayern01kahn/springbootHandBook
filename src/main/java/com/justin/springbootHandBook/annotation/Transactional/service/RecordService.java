package com.justin.springbootHandBook.annotation.Transactional.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RecordService {


    @Transactional
    public void record(){
        throw new IllegalArgumentException("name is forbidden");
    }


}
