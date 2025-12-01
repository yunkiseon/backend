package com.example.memo.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.memo.dto.MemoDTO;
import com.example.memo.entity.Memo;
import com.example.memo.repository.MemoRespository;
import com.example.memo.service.MemoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequiredArgsConstructor
@RequestMapping("/memo")
@Log4j2
@Controller
public class MemoController {

    private final MemoRespository memoRespository;

    private final MemoService memoService;
    private final ModelMapper modelMapper; 

    // MemoController(MemoRespository memoRespository) {
    //     this.memoRespository = memoRespository;
    // }


    @GetMapping("/list")
    public void getList(Model model) {
        log.info("전체 메모 요청");
        List<MemoDTO> list = memoService.readAll(); // 이것으로 메모서비스에 만들어진 readAll을 통해서 dto의 정보를 가져올 수 있다.
        // 여기까지가 service 계층에서 controller 계층으로 가져온 것이며 이제 화면단으로 넘기면 된다. Model에 담아서
        model.addAttribute("list", list);
    }
    
    @GetMapping({"/read", "/modify"})
    public void getRead(@RequestParam Long id, Model model) {
        log.info("memo id {}", id);
        // 하나 조회
        MemoDTO dto = memoService.read(id);
        model.addAttribute("dto", dto);
        // void 의 경우 /memo/read.html 을 찾음, return이면 특정 값을 더해서 찾고
        // 수정 후 보내기하면 post. 삭제 등 할땐 post x 
    }

    @PostMapping("/modify")
    public String postModify(MemoDTO dto, RedirectAttributes rttr) {
        log.info("memo 수정 ()", dto);
        Long id = memoService.modify(dto);

        // memo/read/id?=1 이동할 때 id도 보내야함 
        rttr.addAttribute("id", id);
        return "redirect:/memo/read";
    }
    




    @PostMapping("/remove")
    public String postRemove(@RequestParam Long id, RedirectAttributes rttr) {
        log.info("memo remove id {}", id);
        memoService.remove(id);
        // 삭제 후 목록 보여주기. 기능이 원래 템플릿을 보여주게 되어있다. 
        // 그런데 목록을 보여주고 싶다. 그래서 경로를 /list로 잡는다.
        rttr.addFlashAttribute("msg","메모가 삭제되었습니다.");
        return "redirect:/memo/list";
    }
    
    @GetMapping("/create")
    public String getCreate(@ModelAttribute("dto") MemoDTO dto) {
        log.info("추가 페이지 요청");
        return "/memo/create";
    }
    
    @PostMapping("/create")//valid 검증해줘. dto에서 ~을 검증조건.
    public String postCreate(@ModelAttribute("dto") @Valid MemoDTO dto,BindingResult result, RedirectAttributes rttr) {
        
        log.info("추가 요청 {}", dto);

        // 유효성 검사 조건에 일치하지 않는 경우
        if (result.hasErrors()) {
            return "/memo/create";
        }
        // 일치하는 경우
        
        Long id = memoService.insert(dto);
        rttr.addFlashAttribute("msg", id + "번 메모가 삽입되었습니다.");
        return "redirect:/memo/list";
    }
    
    
}
