package KEYSAT.AttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceRecordController {

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @GetMapping
    public ResponseEntity<List<AttendanceRecord>> getAllAttendanceRecords() {
        return ResponseEntity.ok(attendanceRecordRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceRecord> getAttendanceRecordById(@PathVariable Long id) {
        return attendanceRecordRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AttendanceRecord> addAttendanceRecord(@RequestBody AttendanceRecord attendanceRecord) {
        return ResponseEntity.ok(attendanceRecordRepository.save(attendanceRecord));
    }


}