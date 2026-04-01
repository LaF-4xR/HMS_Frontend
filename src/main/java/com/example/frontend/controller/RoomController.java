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

    // ==========================================
    // MAIN HUB
    // ==========================================
    @GetMapping("")
    public String roomHub() {
        return "room/room";
    }

    // ==========================================
    // READ OPERATIONS (GET)
    // ==========================================

    @GetMapping("/all")
    public String getAllRooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "room/all-rooms";
    }

    // --- Search By ID ---
    @GetMapping("/searchByIdPage")
    public String searchByIdPage() {
        return "room/room-id";
    }

    @GetMapping("/searchById")
    public String searchById(@RequestParam int id, Model model) {
        model.addAttribute("roomById", roomService.getRoomById(id));
        return "room/room-id";
    }

    // --- Search By Type ---
    @GetMapping("/searchByTypePage")
    public String searchByTypePage() {
        return "room/room-type";
    }

    @GetMapping("/searchByType")
    public String searchByType(@RequestParam String roomType, Model model) {
        model.addAttribute("roomsByType", roomService.getRoomsByType(roomType));
        return "room/room-type";
    }

    // ==========================================
    // WRITE OPERATIONS (POST/PUT)
    // ==========================================

    // --- Create Room ---
    @GetMapping("/createForm")
    public String showCreateForm() {
        return "room/room-create";
    }

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

        // Redirects back to the hub after creation
        return "redirect:/entity/soumadwip/Room";
    }

    // --- Update Room ---
    @GetMapping("/updateFormPage")
    public String showUpdateForm() {
        return "room/room-update";
    }

    @PostMapping("/update")
    public String updateRoom(
            @RequestParam int id,   // This acts as the target roomNumber
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

        // Redirects back to the hub after update
        return "redirect:/entity/soumadwip/Room";
    }
}