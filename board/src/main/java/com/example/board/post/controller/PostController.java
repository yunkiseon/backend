package com.example.board.post.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.post.dto.BoardDTO;
import com.example.board.post.dto.PageRequestDTO;
import com.example.board.post.dto.PageResultDTO;
import com.example.board.post.service.BoardService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;



@Log4j2
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostController {
    
    private final BoardService boardService;

    //유효성 검증 +데이터 도착
    //유효성->dto에 notblank, null
    @GetMapping("/create")
    public void getCreate(PageRequestDTO pageRequestDTO, BoardDTO dto) {
        log.info("작성 폼 요청");
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postCreate(@Valid BoardDTO dto, BindingResult result, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
       log.info("작성 {}", dto);
       if (result.hasErrors()) {
        return "board/create";
       } 
       Long bno = boardService.insert(dto);


       rttr.addFlashAttribute("msg", bno + "번 게시글 등록이 완료되었습니다.");
       
        return "redirect:/board/list";
    }
    
    

    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list 요청 {}", pageRequestDTO);
        
        PageResultDTO<BoardDTO> result = boardService.getList(pageRequestDTO);

        model.addAttribute("result", result);

    }

    @GetMapping({"/read","/modify"})
    public void getRead(@RequestParam("bno") Long bno, Model model, PageRequestDTO pageRequestDTO) {
        log.info("get or midfy {}", bno);
        BoardDTO dto = boardService.getRow(bno);
        model.addAttribute("dto", dto);
        model.addAttribute("pageRequestDTO", pageRequestDTO);
    }

    @PreAuthorize("authentication.name == #dto.writerEmail")
    @PostMapping("/modify")
    public String postupdate(BoardDTO dto ,PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("수정 {} {}", dto, pageRequestDTO);
        
        boardService.update(dto);

        rttr.addFlashAttribute("msg", "게시글 수정이 완료되었습니다.");
        rttr.addAttribute("bno", dto.getBno());
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        
        return "redirect:/board/read";
    }
    @PreAuthorize("authentication.name == #dto.writerEmail")
    @PostMapping("/remove")
    public String postdelete(BoardDTO dto ,PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("삭제 {} {}", dto, pageRequestDTO);
        
        boardService.delete(dto);

        rttr.addFlashAttribute("msg", "게시글 삭제가 완료되었습니다.");
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        
        return "redirect:/board/list";
    }
    
    
    
}
