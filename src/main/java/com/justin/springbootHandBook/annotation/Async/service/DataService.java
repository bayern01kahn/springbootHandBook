package com.justin.springbootHandBook.annotation.Async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DataService {

    @Transactional
    public void allStep(){
        saveObj();
        updateObj();
    }

    public void saveObj(){
      log.info("Data Saved");
    }

    public void updateObj(){
        log.info("Data Updated");
    }
}
