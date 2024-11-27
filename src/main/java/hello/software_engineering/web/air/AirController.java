package hello.software_engineering.web.air;

import hello.software_engineering.domain.airport.Airport;
import hello.software_engineering.domain.airroute.AirRoute;
import hello.software_engineering.domain.group.Group;
import hello.software_engineering.domain.member.Member;
import hello.software_engineering.domain.seat.SeatClass;
import hello.software_engineering.domain.ticket.Ticket;
import hello.software_engineering.dto.in.CreateTicketInDto;
import hello.software_engineering.dto.in.FindAirRouteInDto;
import hello.software_engineering.dto.in.GroupCreationRequest;
import hello.software_engineering.repository.GroupRepository;
import hello.software_engineering.repository.MemberRepository;
import hello.software_engineering.repository.TicketRepository;
import hello.software_engineering.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/air")
@RequiredArgsConstructor
public class AirController {

    private final AirportService airportService;
    private final AirRouteService airRouteService;
    private final TicketService ticketService;
    private final MemberService memberService;
    private final GroupService groupService;
    private final TicketRepository ticketRepository;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    @GetMapping
    public String home(Model model) {
        List<Airport> airports = airportService.getAllAirports();
        model.addAttribute("airports", airports);
        return "index";  // src/main/resources/static/index.html로 연결됨
    }



    @GetMapping("/Result")
    public String Result(@RequestParam String departure, @RequestParam String arrival,
                         @RequestParam String departure_day,
                         Model model) {
        FindAirRouteInDto inDto = FindAirRouteInDto.builder()
                .departure(departure)
                .arrival(arrival)
                .departureDay(departure_day)
                .build();

        List<AirRoute> matchingRoutes = airRouteService.findByRoute(inDto);
        if (!matchingRoutes.isEmpty()) {
            model.addAttribute("firstRoute", matchingRoutes.get(0)); // 첫 번째 요소 추가
        }
        model.addAttribute("routes", matchingRoutes);
        return "basic/Result";
    }

    @GetMapping("/{airRouteId}")
    public String airLine(@PathVariable Long airRouteId, Model model) {
        AirRoute findAirRoute = airRouteService.findById(airRouteId);
        model.addAttribute("route", findAirRoute);
        model.addAttribute("seatClass", SeatClass.values());
        return "basic/Airline";
    }

    @PostMapping("/{airRouteId}")
    public String reserve(
            @PathVariable("airRouteId") Long airRouteId,
            @RequestParam("seatClass") String seatClass,
            @RequestParam("adultCount") int adultCount,
            @RequestParam("childCount") int childCount,
            @RequestParam("totalPrice") int totalPrice,
            RedirectAttributes redirectAttributes) {

        CreateTicketInDto inDto = new CreateTicketInDto(airRouteId, seatClass, adultCount, childCount, totalPrice);

        // 예약 처리 로직
        Long ticketId = ticketService.save(inDto);

        // 성인 수와 아이의 수 합산
        int totalPassengers = adultCount + childCount;

        // 합산 값을 RedirectAttributes에 추가
        redirectAttributes.addAttribute("totalPassengers", totalPassengers);

        redirectAttributes.addAttribute("ticketId", ticketId);
        // userInformation 페이지로 리다이렉트
        return "redirect:/air/userInformation";
    }

    @GetMapping("/userInformation")
    public String userInformation(@RequestParam("totalPassengers") int totalPassengers,
                                  @RequestParam("ticketId") Long ticketId,
                                  Model model) {
        // totalPassengers 값을 모델에 추가
        model.addAttribute("totalPassengers", totalPassengers);
        model.addAttribute("ticketId", ticketId);
        return "basic/userInformation";
    }

    @PostMapping("/userInformation")
    public String userInformation(
            @RequestParam("ticketId") Long ticketId,
            @RequestParam("name") List<String> names,
            @RequestParam("gender") List<String> genders,
            @RequestParam("phone") List<String> phones,
            @RequestParam("email") List<String> emails,
            @RequestParam("birthdate") List<String> birthdates,
            RedirectAttributes redirectAttributes) {

        // 사용자 데이터를 처리
        List<Long> memberIds = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            Member member = new Member(names.get(i), genders.get(i), emails.get(i), phones.get(i), birthdates.get(i));
            member.setSN("A" + (i + 1));
            memberService.save(member);
            memberIds.add(member.getId());
        }

        // 그룹 생성
        String groupEmail = emails.get(0);
        GroupCreationRequest request = new GroupCreationRequest();
        request.setGroupEmail(groupEmail);
        request.setMembersIds(memberIds);

        Group savedGroup = groupService.createGroup(request);
        Ticket findTicket = ticketRepository.findById(ticketId).get();
        findTicket.setGroup(savedGroup);
        ticketRepository.save(findTicket);
        Long groupId = savedGroup.getId();
        redirectAttributes.addAttribute("groupId", groupId);
        return "redirect:/air/payment";
    }

    @GetMapping("/payment")
    public String payment(@RequestParam("groupId") Long groupId, Model model,
                          RedirectAttributes redirectAttributes) {
        Group findGroup = groupRepository.findById(groupId).get();
        Ticket findTicket = ticketService.findByGroupId(findGroup.getId());

        AirRoute findAirRoute = findTicket.getAirRoute();

        Long findTicketId=findTicket.getId();
        model.addAttribute("airRoute", findAirRoute);
        model.addAttribute("ticket", findTicket);
        redirectAttributes.addAttribute("ticketId", findTicketId);
        return "basic/Payment";

    }

    @GetMapping("/success")
    public String success(@RequestParam("ticketId") Long ticketId, Model model) {
        Ticket findTicket = ticketRepository.findById(ticketId).get();
        model.addAttribute("ticket", findTicket);
        return "basic/Success";
    }

    @GetMapping("/search")
    public String Search() {
        return "basic/search";
    }

    @GetMapping("/search/result")
    public String searchResult(@RequestParam("pin") String pin, Model model) {
        // 핀 번호로 예약 정보를 검색
        Ticket ticket = ticketRepository.findByPinCode(pin);
        AirRoute findAirRoute = ticket.getAirRoute();

        Long findGroupId=ticket.getGroup().getId();
        List<Member> members =memberRepository.findByGroupId(findGroupId);



        model.addAttribute("airRoute", findAirRoute);
        model.addAttribute("members", members);
        // 검색 결과를 모델에 추가
        model.addAttribute("ticket", ticket);

        return "basic/searchResult"; // 결과 페이지 렌더링
    }

}
