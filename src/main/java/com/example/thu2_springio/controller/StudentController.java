package com.example.thu2_springio.controller;

import com.example.thu2_springio.dto.StudentDTO;
import com.example.thu2_springio.dto.StudentImageDto;
import com.example.thu2_springio.model.Student;
import com.example.thu2_springio.model.StudentImage;
import com.example.thu2_springio.model.XepLoai;
import com.example.thu2_springio.response.ApiResponse;
import com.example.thu2_springio.response.StudentListResponse;
import com.example.thu2_springio.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.invoker.UrlArgumentResolver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/student")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class StudentController {

    private final StudentService service;
    @GetMapping
    public ResponseEntity<?> studentList(){
        List<Student> students = service.getallStudent();
        if(students.size() == 0){
            ApiResponse apiResponse = ApiResponse
                    .builder()
                    .data(null)
                    .message("No data found")
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        return ResponseEntity.ok().body(students);
    }


    @GetMapping("/list")
    public ResponseEntity<?> getListStudentPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "0") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentPage = service.getStudents(pageable);
        int totalPages = studentPage.getTotalPages();

        StudentListResponse studentListResponse = StudentListResponse
                .builder()
                .totalPages(totalPages)
                .studentList(studentPage.getContent())
                .build();
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(HttpStatus.OK.value())
                .message("Get list student success")
                .data(studentListResponse)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveStudent(@Valid @RequestBody StudentDTO studentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> listErro = bindingResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            ApiResponse apiResponse = ApiResponse
                    .builder()
                    .data(listErro)
                    .message("Validation failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        Student student = service.saveStudent(studentDTO);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(student)
                .message("Insert success")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") Long id,@Valid @RequestBody StudentDTO studentDTO
            , BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<String> listErro = bindingResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            ApiResponse apiResponse = ApiResponse
                    .builder()
                    .data(listErro)
                    .message("Validation failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        Student student = service.updateStudent(id, studentDTO);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(student)
                .message("Update Successful")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id){
        Student student = service.getStudentById(id);
        if(student == null){
            ApiResponse apiResponse = ApiResponse
                    .builder()
                    .data(id)
                    .message("Student not found")
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        service.removeStudent(id);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(id)
                .message("delete successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getStudentByName(@RequestParam("ten") String ten){
        List<Student> students = service.fingByThanhPhoOrTen(ten);
        if(students.size() == 0){
            ApiResponse apiResponse = ApiResponse
                    .builder()
                    .data(null)
                    .message("No data found")
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }

        return ResponseEntity.ok().body(students);
    }

    @GetMapping("/getbynamsinh")
    public ResponseEntity<?> getStudentByNamSinh(@RequestParam("namSinh1") int namSinh1, @RequestParam("namsinh2") int namsinh2) {
        List<Student> students = service.findByNgaySinhBetween(namSinh1, namsinh2);
        if (students.isEmpty()) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(null)
                    .message("No data found")
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(students)
                .message("Data found")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
    @GetMapping("/getbyxeploai")
    public ResponseEntity<?> getStudentByXepLoai(@RequestParam("xepLoai") XepLoai xepLoai) {
        List<Student> students = service.findByXepLoai(xepLoai);
        if (students.isEmpty()) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(null)
                    .message("No data found")
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(students)
                .message("Data found")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchStudent(
            @RequestParam(value = "xepLoai", required = false) XepLoai xepLoai,
            @RequestParam(value = "ten", required = false) String ten,
            @RequestParam(value = "thanhPho", required = false) String thanhPho,
            @RequestParam(value = "startYear", required = false) int startYear,
            @RequestParam(value = "endYear", required = false) int endYear
    ){
        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.searchStudents(xepLoai, ten,thanhPho,startYear,endYear))
                .message("search successful")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/getimage/{id}")
    public ResponseEntity<?> getStudentImage(@PathVariable("id") Long studentId) {
        List<StudentImage> studentImages = service.getStudentImages(studentId);
        if (studentImages.isEmpty()) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(null)
                    .message("No data found")
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(studentImages)
                .message("Data found")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

//    @PostMapping("/uploadimage/{id}")
//    public ResponseEntity<?> saveStudentImage(@PathVariable("id") Long id,
//                                              @Valid @RequestBody StudentImageDto studentImageDto, BindingResult result) {
//        if(result.hasErrors()){
//            List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
//            ApiResponse apiResponse = ApiResponse.builder()
//                    .data(errors)
//                    .message("Validation failed")
//                    .status(HttpStatus.BAD_REQUEST.value())
//                    .build();
//            return ResponseEntity.badRequest().body(apiResponse);
//        }
//        ApiResponse apiResponse = ApiResponse.builder()
//                .data(service.saveStudentImage(id, studentImageDto))
//                .message("upload successful")
//                .status(HttpStatus.OK.value())
//                .build();
//        return ResponseEntity.ok(apiResponse);
//    }



//    @PostMapping("/uploadimage/{id}")
//    public ResponseEntity<?> saveStudentImage(@PathVariable("id") Long id,
//                                              @ModelAttribute("files") MultipartFile files) throws IOException {
//        String filename = storeFile(files);
//        StudentImageDto studentImageDto = StudentImageDto.builder()
//                .imageURL(filename)
//                .build();
//        ApiResponse apiResponse = ApiResponse.builder()
//                .data(service.saveStudentImage(id, studentImageDto))
//                .message("upload successful")
//                .status(HttpStatus.OK.value())
//                .build();
//        return ResponseEntity.ok(apiResponse);
//    }
//    private String storeFile(MultipartFile file) throws IOException {
//
//        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
//
//        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFilename;
//
//        Path uploadDir = Paths.get("upload");
//
//        if (!Files.exists(uploadDir)) {
//            Files.createDirectories(uploadDir);
//        }
//        Path filePath = Paths.get(uploadDir.toString(),uniqueFileName);
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//        return uniqueFileName;
//    }
    @PostMapping("/uploadimage/{id}")
    public ResponseEntity<?> saveStudentImage(@PathVariable("id") Long id,
                                              @ModelAttribute("files") List<MultipartFile> files) throws IOException {
        List<StudentImage> studentImages = new ArrayList<>();
        int count = 0;
        for (MultipartFile file : files) {
            if(file!=null){
                if(file.getSize()==0){
                    count++;
                    continue;
                }
                String filename = storeFile(file);
                StudentImageDto studentImageDto = StudentImageDto.builder()
                        .imageURL(filename)
                        .build();
                StudentImage studentImage = service.saveStudentImage(id, studentImageDto);
                studentImages.add(studentImage);
            }
        }
//        String filename = storeFile(files);

//        StudentImageDto studentImageDto = StudentImageDto.builder()
//                .imageURL(filename)
//                .build();

        if(count==1){
            throw new IllegalArgumentException("File is empty");
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(studentImages)
                .message("upload successful")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    private String storeFile(MultipartFile file) throws IOException {

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());

        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFilename;

        Path uploadDir = Paths.get("upload");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path filePath = Paths.get(uploadDir.toString(),uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> getStudentImage(@PathVariable("imageName") String imageName) {
        try {
            Path filePath = Paths.get("upload/"+imageName);
            UrlResource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }
            else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpeg").toUri()));
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteimage/{id}")
    public ResponseEntity<?> deleteStudentImage(@PathVariable Long id) {
//         StudentImage image = service.getStudentImageById(id);
//         if(image==null){
//             ApiResponse apiResponse = ApiResponse.builder()
//                     .data(null)
//                     .message("not found")
//                     .status(HttpStatus.NOT_FOUND.value())
//                     .build();
//             return ResponseEntity.badRequest().body(apiResponse);
//         }
         service.removeStudentImage(id);
         ApiResponse apiResponse = ApiResponse.builder()
                 .data(id)
                 .message("delete successful")
                 .status(HttpStatus.OK.value())
                 .build();
         return ResponseEntity.ok(apiResponse);
    }
}
