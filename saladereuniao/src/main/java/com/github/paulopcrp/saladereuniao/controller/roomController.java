package com.github.paulopcrp.saladereuniao.controller;

import com.github.paulopcrp.saladereuniao.exception.ResourceNofFoundException;
import com.github.paulopcrp.saladereuniao.model.Room;
import com.github.paulopcrp.saladereuniao.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class roomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable(value = "id") long roomId)
        throws ResourceNofFoundException{
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourceNofFoundException("Room not found:" + roomId));
        return ResponseEntity.ok().body(room);
    }

    @PostMapping("/rooms")
    public Room createRoom(@Valid @RequestBody Room room) {
        return roomRepository.save(room);
    }
    @PutMapping("rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable(value = "id") Long roomId, @Valid @RequestBody Room roomDetails)
        throws ResourceNofFoundException{
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourceNofFoundException("Room not found for this ID::" + roomId));
        room.setName(roomDetails.getName());
        room.setDate(roomDetails.getDate());
        room.setStartHour(roomDetails.getStartHour());
        room.setEndHour(roomDetails.getEndHour());
        final Room updateRoom = roomRepository.save(room);
        return ResponseEntity.ok(updateRoom);
    }

    public Map<String, Boolean> deleteRoom(@PathVariable(value = "id") Long roomId)
        throws ResourceNofFoundException {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourceNofFoundException("Room not found for this ID::" + roomId));
        roomRepository.delete(room);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return response;
    }

}
