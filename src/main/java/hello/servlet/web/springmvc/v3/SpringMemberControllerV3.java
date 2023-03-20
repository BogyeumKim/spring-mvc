package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    /* ModelAndView 타입으로 반환해도 되지만 그냥 String으로 반환시켜도 스프링 애너테이션기반 컨트롤러를 쓰면 뷰이름으로 알고 프로세스로 진행됨. */

//    @RequestMapping(value = "/new-form",method = RequestMethod.GET) // GET인 경우에만 호출됨
    @GetMapping("/new-form")
    public String newForm() {
        return "new-form";
    }

//    @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public String members(Model model) {

        List<Member> members = memberRepository.findAll();

        model.addAttribute("members", members);
        return "members";
    }

//    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @PostMapping("/save")
    public String save(@RequestParam("username") String username,@RequestParam("age") int age, Model model) {

        Member member = new Member(username,age);
        memberRepository.save(member);

        model.addAttribute("member", member);
        return "save-result";

    }
}
