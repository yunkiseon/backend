package com.example.memo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.memo.dto.MemoDTO;
import com.example.memo.entity.Memo;
import com.example.memo.repository.MemoRespository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MemoService {
    
    @Autowired
    private MemoRespository memoRespository;

    // memo 전체 조회
    public List<MemoDTO> readAll(){
        List<Memo> memos = memoRespository.findAll();
        // entity는 service에서 repository로 넘길 때, 혹은 repository 에서 service로 받을 때만 쓰고 싶다.
        // service에서 controller로 넘길 때, controller에서 service로 넘길 때는 dto로 하고 싶다. 
        // 리턴하기 전 Memo entity -> MemoDTO 로 변경후 리턴
        // 담겨 있는 리스트 내용을 하나씩 빼서 dto로 바꾸고 다시 list.add 하면 된다. 
        // memo.getId memo.getText memo.getCreateDate memo.getUpdateDate
        List<MemoDTO> list = new ArrayList<>();
        
        for (Memo memo : memos) {
            MemoDTO dto = MemoDTO.builder()
            .id( memo.getId())
            .text( memo.getText())
            .createDate(memo.getCreateDate())
            .updateDate(memo.getUpdateDate())
            .build();
            list.add(dto);
        }
        return list;

    }

}
