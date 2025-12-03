package com.example.memo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.memo.dto.MemoDTO;
import com.example.memo.entity.Memo;
import com.example.memo.repository.MemoRespository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor//자동으로 생성자 생성
@Log4j2
@Service
public class MemoService {
    
    // @Autowired
    // private MemoRespository memoRespository;
    // @Autowired
    // private ModelMapper modelMapper;

    private final MemoRespository memoRespository;
    private final ModelMapper modelMapper;

    // memo 전체 조회

    public List<MemoDTO> readAll(){
        List<Memo> memos = memoRespository.findAll();
        // entity는 service에서 repository로 넘길 때, 혹은 repository 에서 service로 받을 때만 쓰고 싶다.
        // service에서 controller로 넘길 때, controller에서 service로 넘길 때는 dto로 하고 싶다. 
        // 리턴하기 전 Memo entity -> MemoDTO 로 변경후 리턴
        // 담겨 있는 리스트 내용을 하나씩 빼서 dto로 바꾸고 다시 list.add 하면 된다. 
        // memo.getId memo.getText memo.getCreateDate memo.getUpdateDate
        // List<MemoDTO> list = new ArrayList<>();
        


        // for (Memo memo : memos) {
        //     // MemoDTO dto = MemoDTO.builder()
        //     // .id( memo.getId())
        //     // .text( memo.getText())
        //     // .createDate(memo.getCreateDate())
        //     // .updateDate(memo.getUpdateDate())
        //     // .build();

        //     MemoDTO dto = modelMapper.map(memo, MemoDTO.class);

        //     list.add(dto);
        // }
        List<MemoDTO> list = memos.stream().map(memo->modelMapper.map(memo, MemoDTO.class))
        .collect(Collectors.toList());
        return list;

    }
    public MemoDTO read(Long id){
    //  Memo memo = memoRespository.findById(id).get(); 아래와 동일
    // Optional<Memo> result = memoRespository.findById(id);
    // Memo memo = null;
    // if (result.isPresent()) {
    //     memo = result.get();
    // } 아래와 동일
    Memo memo = memoRespository.findById(id).orElseThrow();// 있으면 가져와라 없으면 날려라.
    // entity => dto 변환 후 리턴
    return modelMapper.map(memo, MemoDTO.class);
    }
    
    public Long modify(MemoDTO dto){
        // 대상 찾기
        Memo memo = memoRespository.findById(dto.getId()).orElseThrow();
        // 변경
        memo.changeText(dto.getText());
        // memoRespository.save(memo);
        // return memo.getId();
        return memoRespository.save(memo).getId();
    }
    public void remove(Long id){
        memoRespository.deleteById(id);
    }
    public Long insert(MemoDTO dto){

        // dto => entity
        Memo memo = modelMapper.map(dto, Memo.class);
        return memoRespository.save(memo).getId();
    }
}
