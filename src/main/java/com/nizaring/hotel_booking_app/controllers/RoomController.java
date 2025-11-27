package com.nizaring.hotel_booking_app.controllers;

import com.nizaring.hotel_booking_app.dtos.Response;
import com.nizaring.hotel_booking_app.dtos.RoomDTO;
import com.nizaring.hotel_booking_app.enums.RoomType;
import com.nizaring.hotel_booking_app.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@AllArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addRoom(
            @RequestParam Integer roomNumber,
            @RequestParam RoomType type,
            @RequestParam BigDecimal pricePerNight,
            @RequestParam Integer capacity,
            @RequestParam String  description,
            @RequestParam MultipartFile imageFile
    ){
        var roomDTO = new RoomDTO();
        roomDTO.setRoomNumber(roomNumber);
        roomDTO.setType(type);
        roomDTO.setPricePerNight(pricePerNight);
        roomDTO.setCapacity(capacity);
        roomDTO.setDescription(description);

        return  ResponseEntity.ok(roomService.addRoom(roomDTO, imageFile));

    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(
            @RequestParam (value = "roomNumber", required = false) Integer roomNumber,
            @RequestParam (value = "type", required = false) RoomType type,
            @RequestParam (value = "pricePerNight", required = false) BigDecimal pricePerNight,
            @RequestParam (value = "capacity", required = false) Integer capacity,
            @RequestParam (value = "description", required = false) String  description,
            @RequestParam (value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam (value = "id", required = true) Long id
    ){
        var roomDTO = new RoomDTO();
        roomDTO.setId(id);
        roomDTO.setRoomNumber(roomNumber);
        roomDTO.setType(type);
        roomDTO.setPricePerNight(pricePerNight);
        roomDTO.setCapacity(capacity);
        roomDTO.setDescription(description);

        return  ResponseEntity.ok(roomService.updateRoom(roomDTO, imageFile));

    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllRooms(){
        return ResponseEntity.ok(roomService.getAllRooms());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response> getRoomById(@PathVariable Long id){
        return ResponseEntity.ok(roomService.getRoomById(id));
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable Long id){
        return ResponseEntity.ok(roomService.deleteRoom(id));
    }


    @GetMapping("/available")
    public ResponseEntity<Response> getAvailableRooms(

            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam(required = false) RoomType roomType
    ){
        return ResponseEntity.ok(roomService.getAvailableRooms(checkInDate, checkOutDate, roomType));
    }


    @GetMapping("/types")
    public ResponseEntity<List<RoomType>> getAllRoomTypes(){
        return ResponseEntity.ok(roomService.getAllRoomTypes());
    }



    @GetMapping("/search")
    public ResponseEntity<Response> searchRoom(@RequestParam String input){
        return ResponseEntity.ok(roomService.searchRoom(input));
    }

}
