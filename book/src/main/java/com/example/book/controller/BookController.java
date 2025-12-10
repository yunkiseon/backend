package com.example.book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.service.BookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RequestMapping("/book")
@Log4j2
@Controller
public class BookController {

    private final BookService bookService;


    @GetMapping("/register")
    public void getRegister(BookDTO bookDTO) {
        log.info("등록화면 보여주기");
        // 만약 @ModelAttribute(dto)를 BookDTO 옆에 넣고 Post에서도 동일하게하면
        // register에서 obj bookDTO가 아닌 dto로, 밸류값 등에서도 dto.~ 하면된다.
    }

    @PostMapping("/register")
    public String postRegister(@Valid BookDTO dto, BindingResult result, RedirectAttributes rttr) {
       log.info("회원가입 요청");
        
        if (result.hasErrors()) {
            return "book/register";
        }
        String title = bookService.create(dto);
        rttr.addFlashAttribute("msg", title + "도서가 등록되었습니다.");
        return "redirect:/book/list";
    }
    
    
    @GetMapping("/list")
    public void getList(Model model, PageRequestDTO pageRequestDTO

    ) {
        log.info("목록화면 보여주기");
        PageResultDTO<BookDTO> result = bookService.getList(pageRequestDTO);
        model.addAttribute("result", result);
    }
    
    @GetMapping({"/read", "/modify"})
    public void getRead(@RequestParam Long id, Model model) {
        log.info("도서 상세 조회 {}", id);
        BookDTO dto = bookService.readId(id);
        model.addAttribute("dto", dto);
    }
    @PostMapping("/modify")
    public String postModify(BookDTO dto , RedirectAttributes rttr) {
        log.info("도서 수정 {}", dto);
        Long id = bookService.update(dto);
        // 위 코드가 된다는 것은 dto 안에 id가 있고 그것을 꺼내준다는 것이다. 그렇기때문에
        // 주소창에 넣을 때 쉽게 id 정보만 쓸 수 있다.
        // 하지만 id가 html에 안적히는 상황에서 remove의 경우에는 id를 따로 해줄 필요가 있다
        // 때문에 modifyhtml에 안보이는 id 값을 넣어주었다. 
        rttr.addFlashAttribute("msg", "도서 정보가 수정되었습니다.");
        rttr.addAttribute("id", id);
        return "redirect:/book/read";
    }
    
    @PostMapping("/remove")
    public String postRemove(@RequestParam("id") Long id, RedirectAttributes rttr) {
        bookService.delete(id);
        rttr.addFlashAttribute("msg", "도서가 삭제되었습니다.");
        return "redirect:/book/list";
    }
    
    
    
}
