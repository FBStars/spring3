package Controller;

import DAO.PhonebookDAO;
import VO.PhonebookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/phonebook")
public class PhonebookController {

    @Autowired
    private PhonebookDAO phonebookDAO;

    // 전체 출력 및 검색 기능
    @GetMapping("/list")
    public String listPhonebooks(@RequestParam(value = "search", required = false) String search, Model model) throws SQLException {
        List<PhonebookVO> phonebooks;

        if (search != null && !search.isEmpty()) {
            // 검색 기능
            phonebooks = phonebookDAO.searchPhonebook(search); // 검색어에 맞는 항목을 조회
        } else {
            phonebooks = phonebookDAO.selectAllPhonebook(); // 검색어가 없을 때 전체 전화번호부 출력
        }

        model.addAttribute("phonebooks", phonebooks);
        return "inputForm"; // JSP 파일 이름 반환
    }

    // 입력 폼 처리
    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("phonebook", new PhonebookVO());
        return "inputForm";
    }

    // 저장
    @PostMapping("/save")
    public String savePhonebook(@ModelAttribute("phonebook") PhonebookVO phonebook) throws SQLException {
        phonebookDAO.insertPhonebook(phonebook);
        return "redirect:/phonebook/list";
    }

    // 선택 출력
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) throws SQLException {
        PhonebookVO phonebook = phonebookDAO.selectPhonebookById(id);
        model.addAttribute("phonebook", phonebook);
        return "inputForm";
    }

    // 수정
    @PostMapping("/update")
    public String updatePhonebook(@ModelAttribute("phonebook") PhonebookVO phonebook) throws SQLException {
        phonebookDAO.updatePhonebook(phonebook);
        return "redirect:/phonebook/list";
    }

    // 삭제
    @PostMapping("/delete/{id}")
    public String deletePhonebook(@PathVariable int id) throws SQLException {
        phonebookDAO.deletePhonebook(id);
        return "redirect:/phonebook/list";
    }
}
