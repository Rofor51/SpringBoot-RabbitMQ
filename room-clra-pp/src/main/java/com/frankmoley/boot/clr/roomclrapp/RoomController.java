package com.frankmoley.boot.landon.roomwebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("room")
public class RoomController {
    private RoomService rooms;

    @Autowired
    public RoomController(RoomService rooms) {
        this.rooms = rooms;
    }

    @GetMapping
    public String getAllRooms(Model model){
        model.addAttribute("rooms", rooms.getAllRooms());
        return "rooms";
    }
}
