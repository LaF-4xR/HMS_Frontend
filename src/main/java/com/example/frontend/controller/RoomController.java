package com.example.frontend.controller;

import com.example.frontend.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("entity/soumadwip/Room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public String roomPage(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "room";
    }

    // ✅ CREATE ROOM
    @PostMapping("/create")
    public String createRoom(
            @RequestParam int blockCode,
            @RequestParam int blockFloor,
            @RequestParam int roomNumber,
            @RequestParam String roomType,
            @RequestParam boolean unavailable
    ) {

        Map<String, Object> room = new LinkedHashMap<>();
        room.put("blockCode", blockCode);
        room.put("blockFloor", blockFloor);
        room.put("roomNumber", roomNumber);
        room.put("roomType", roomType);
        room.put("unavailable", unavailable);

        roomService.createRoom(room);

        return "redirect:/entity/soumadwip/Room";
    }

    // ✅ UPDATE ROOM
    @PostMapping("/update")
    public String updateRoom(
            @RequestParam int id,   // this is roomNumber
            @RequestParam int blockCode,
            @RequestParam int blockFloor,
            @RequestParam String roomType,
            @RequestParam boolean unavailable
    ) {

        Map<String, Object> room = new LinkedHashMap<>();
        room.put("blockCode", blockCode);
        room.put("blockFloor", blockFloor);
        room.put("roomType", roomType);
        room.put("unavailable", unavailable);

        roomService.updateRoom(id, room);

        return "redirect:/entity/soumadwip/Room";
    }

    @GetMapping("/searchByType")
    public String searchByType(@RequestParam String roomType, Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("roomsByType", roomService.getRoomsByType(roomType));
        return "room";
    }

    @GetMapping("/searchById")
    public String searchById(@RequestParam int id, Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("roomById", roomService.getRoomById(id));
        return "room";
    }
}