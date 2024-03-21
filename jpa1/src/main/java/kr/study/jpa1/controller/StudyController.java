package kr.study.jpa1.controller;

import kr.study.jpa1.domain.Member;
import kr.study.jpa1.domain.StudyRecord;
import kr.study.jpa1.form.StudyForm;
import kr.study.jpa1.service.MemberService;
import kr.study.jpa1.service.StudyRecordService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {

    private final MemberService memberService;
    private final StudyRecordService recordService;

    @GetMapping
    public String addForm(Model model){
        List<Member> members = memberService.getList();
        if(members == null){
            return "redirect:/member";
        }
        model.addAttribute("members" , members);
        model.addAttribute("curdate" , LocalDate.now());
        model.addAttribute("curtime" , LocalTime.now());

        return "study/addForm";
    }
    @PostMapping
    public String addStudyRecord(StudyForm form){

        log.trace("studyForm = {}" , form);
        log.trace("localDate={}, localTime={}", LocalDate.now() , LocalTime.now());

        Member member = memberService.findByLoginId(form.getMemberLoginId());
        if(member == null){
            log.error(" { } 로그인 아이디가 존재하지 않음" , form.getMemberLoginId());
            return "redirect:/";
        }

        recordService.saveRecord(form, member);

        return "redirect:/";
    }

    @GetMapping("/records")
    public String getAllList(Model model){
        List<Member> members = memberService.getList();
        if(members == null){
            return "redirect:/";
        }

        List<StudyRecord> list = recordService.testAll(); // recordService.getAllRecords();

        if(list == null){
            return "redirect:/";
        }
        for(StudyRecord s : list){
            System.out.println("s = " + s);
        }

        model.addAttribute("list" , list);
        
        return "study/list";
    }

    @GetMapping("/{keyId}")
    public String updateRecord(@PathVariable Long keyId , Model model){
        StudyRecord record = recordService.getOneRecord(keyId);
        if(record == null){
            return "redirect:/";
        }
        model.addAttribute("record" , record);
        model.addAttribute("curdate" , LocalDate.now());
        return "study/updateForm";
    }

    @PostMapping("/update")
    public String updateForm(StudyForm form, @RequestParam Long id){

        log.trace("form={} , id={}", form , id);
        StudyRecord record = recordService.getOneRecord(id);
        if(record == null){
            log.error("msg = {}" , "레코드 값 못 찾음");
            return "redirect:/";
        }
        recordService.updateRecord(form, record);

        return "redirect:/study/records";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteRecord(@PathVariable Long id){
        recordService.deleteRecord(id);
        return "ok";
    }
}
