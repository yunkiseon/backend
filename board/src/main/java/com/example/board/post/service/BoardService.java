package com.example.board.post.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.member.entity.Member;
import com.example.board.post.dto.BoardDTO;
import com.example.board.post.dto.PageRequestDTO;
import com.example.board.post.dto.PageResultDTO;
import com.example.board.post.entity.Board;
import com.example.board.post.repository.BoardRepository;
import com.example.board.reply.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    
    // crud
    @Transactional(readOnly = true)
    public PageResultDTO<BoardDTO> getList(PageRequestDTO requestDTO){
        Pageable pageable = PageRequest.of(requestDTO.getPage() -1, requestDTO.getSize(),Sort.by("bno").descending());
        // Page<Board> result = boardRepository.findAll(pageable);
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
        // 서비스는 데이터를 받아서 controller 로 보낼 때 필요한 정보만 선별해서 보내기 + entity -> dto 하는 작업을 한다
        Function<Object[], BoardDTO> f = en-> entityToDTO((Board) en[0],(Member) en[1],(Long) en[2]);
        List<BoardDTO> dtoList = result.stream().map(f).collect(Collectors.toList());
        long totalCount = result.getTotalElements();
        PageResultDTO<BoardDTO> pageResultDTO = PageResultDTO.<BoardDTO>withAll()
        .dtoList(dtoList)
        .pageRequestDTO(requestDTO)
        .totalCount(totalCount)
        .build();

        return pageResultDTO;
    }


    @Transactional(readOnly = true)
    public BoardDTO getRow(Long bno){
        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        BoardDTO dto = entityToDTO((Board) arr[0],(Member) arr[1],(Long) arr[2]);
        return dto;

    }

    @Transactional
    public void update(BoardDTO dto){
        Board board = boardRepository.findById(dto.getBno()).get();
        board.changeTitle(dto.getTitle());
        board.changeContent(dto.getContent());
        // boardRepository.save(board);
        //dirty-checking 돌아서 알아서 save가 호출된다.

    }
    public void insert(BoardDTO dto){
        
    }
    @Transactional
    public void delete(BoardDTO dto){
        // 게시글 삭제
        // 자식으로 댓글 존재
        replyRepository.deleteByBno(dto.getBno());
        boardRepository.deleteById(dto.getBno());

    }

    // entity->dto
    private BoardDTO entityToDTO(Board board, Member member, Long replyCnt){
        BoardDTO dto = BoardDTO.builder()
        .bno(board.getBno())
        .title(board.getTitle())
        .content(board.getContent())
        .writerEmail(member.getEmail())
        .writerName(member.getName())
        .createDateTime2(board.getCreateDateTime2())
        .updateDateTime(board.getUpdateDateTime())
        .replyCnt(replyCnt != null ? replyCnt.intValue() : 0)//intvalue는 int로 형변환
        .build();
        return dto;
    }

}
