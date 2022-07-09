package id.ac.uin.student.controller;

import id.ac.uin.student.entity.Student;
import id.ac.uin.student.exception.StudentNotFoundException;
import id.ac.uin.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Tag(name = "Student", description = "Endpoints for managing Student")
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public  StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @Operation(
            summary = "List All Student",
            description = "List All Student.",
            tags = {"Student"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Student.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public List<Student> getAllStudents(){
       return studentService.getAllStudent();
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get Student by ID",
            description = "Get Student by ID",
            tags = {"Student"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Student.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public Student getStudentById (@PathVariable("id") @Min(1) Long id){
        Student std = studentService.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with " + id + " is Not Found!"));
        return std;
    }

    @PostMapping
    @Operation(
            summary = "Add new Student",
            description = "Add new Student",
            tags = {"Student"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Student.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public Student addStudent (@Valid @RequestBody Student std){
        return studentService.save(std);
    }

    @PutMapping(value = "/{id}")
    @Operation(
            summary = "Update Student by ID",
            description = "Update Student by ID",
            tags = {"Student"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Student.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public Student updateStudent (@PathVariable("id") @Min(1) Long id, @Valid @RequestBody Student newStd){
        Student student = studentService.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with" + id + " is Not Found"));
        student.setFirstName(newStd.getFirstName());
        student.setLastName(newStd.getLastName());
        student.setEmail(newStd.getEmail());
        student.setPhoneNumber(newStd.getPhoneNumber());
        return studentService.save((student));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Delete Student by ID",
            description = "Delete Student by ID",
            tags = {"Student"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Student.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public String deleteStudent(@PathVariable("id") @Min(1) Long id){
        Student std = studentService.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with" + id + " is Not Found!"));
        studentService.deleteById(std.getId());
        return "Student with ID :" + id + "is deleted";
    }
}
