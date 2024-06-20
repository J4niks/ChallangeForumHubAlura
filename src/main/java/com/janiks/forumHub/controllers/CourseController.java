package com.janiks.forumHub.controllers;

import com.janiks.forumHub.domain.course.Course;
import com.janiks.forumHub.dtos.CourseCreationData;
import com.janiks.forumHub.dtos.CourseData;
import com.janiks.forumHub.infra.exception.ValidationException;
import com.janiks.forumHub.repositories.CourseRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Cursos - Necessário permissão de ADMIN para acessar métodos que não sejam 'GET' ")
public class CourseController {

    @Autowired
    private CourseRepository repository;


    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar", description = "Cadastrar um novo curso")
    public ResponseEntity registerCourse(@RequestBody @Valid CourseCreationData data){
        if(this.repository.existsByName(data.name())){
            throw new ValidationException("Um curso com esse nome já está cadastrado");
        }
        var course = new Course(null,data.name(),data.category());
        this.repository.save(course);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(course.getId())
                .toUri();
        return ResponseEntity.created(location).body(new CourseData(course));
    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Uma lista com todos os cursos cadastrados")
    public ResponseEntity<Page<CourseData>> getAllCourses(@PageableDefault(size= 10, sort = {"category","name"}) Pageable pageable){
        var courses = this.repository.findAll(pageable).map(CourseData::new);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Procurar por id", description = "Mostra o curso com o id escolhido")
    public ResponseEntity getCourse(@PathVariable Long id){
        var course = this.repository.findById(id);
        if(course.isPresent()) {
            return ResponseEntity.ok(new CourseData(course.get()));
        }
        return ResponseEntity.badRequest().body("Curso não existe");
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Editar", description = "Editar curso com id escolhido")
    public ResponseEntity editCourse(@RequestBody @Valid CourseData data, @PathVariable Long id){
        var course = this.repository.getReferenceById(id);
        course.update(data);
        return ResponseEntity.ok(new CourseData(course));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Apagar", description = "Apagar curso com id escolhido")
    public ResponseEntity deleteCourse(@PathVariable Long id){
        var course = this.repository.findById(id);
        if(course.isPresent()){
            this.repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body("Curso não existe no banco de dados");
    }
}
