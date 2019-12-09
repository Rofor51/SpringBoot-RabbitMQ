package com.frankmoley.boot.landon.roomwebapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {
    private RoomService roomService;

    @Autowired
    public ApiController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("rooms")
    public List<Room> getRooms() {
        return roomService.getAllRooms();
    }
}
