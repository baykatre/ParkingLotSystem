package co.anilozturk.ParkingLotSystem.system;

import co.anilozturk.ParkingLotSystem.generic.Response;
import co.anilozturk.ParkingLotSystem.system.model.LeaveInput;
import co.anilozturk.ParkingLotSystem.system.model.ParkInput;
import co.anilozturk.ParkingLotSystem.system.model.ParkingOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/system")
public class SystemController {

    private final SystemService systemService;

    @PostMapping("/park")
    ResponseEntity<Response<ParkingOutput>> park(@RequestBody ParkInput input){

        final ParkingOutput output = systemService.park(input);
        return ResponseEntity.ok(Response.from(output));
    }

    @PostMapping("/leave")
    ResponseEntity<Response<ParkingOutput>> leave(@RequestBody LeaveInput input){

        final ParkingOutput output = systemService.leave(input);
        return ResponseEntity.ok(Response.from(output));
    }

    @GetMapping("/status")
    ResponseEntity<Response<ParkingOutput>> status(){

        final ParkingOutput output = systemService.status();
        return ResponseEntity.ok(Response.from(output));
    }
}
