package com.hasini.PersonalResourceLinkHub;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

// 1. Unified Model Class placed inside the same file to prevent Jackson mapping errors!
class Resource {
    private int id;
    private String title;
    private String url;
    private String category;

    public Resource() {} // Required by Jackson JSON serializer

    public Resource(int id, String title, String url, String category) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.category = category;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}

// 2. Main REST Controller
@RestController
@RequestMapping("/api/newresources")
@CrossOrigin(origins = "*")
public class ResourceController {

    private final List<Resource> resourceList = new ArrayList<>();
    private int currentId = 1;

    public ResourceController() {
        // Lowercase defaults match our custom grouping frontend logic perfectly
        resourceList.add(new Resource(currentId++, "Spring Initializr", "https://start.spring.io", "backend"));
        resourceList.add(new Resource(currentId++, "MDN Web Docs", "https://developer.mozilla.org", "frontend"));
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getAll() {
        return ResponseEntity.ok(resourceList); //
    }

    @PostMapping
    public ResponseEntity<Resource> create(@RequestBody Resource newResource) {
        newResource.setId(currentId++);
        resourceList.add(newResource);
        return ResponseEntity.status(HttpStatus.CREATED).body(newResource); // Status 201
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean removed = resourceList.removeIf(r -> r.getId() == id);
        if (removed) {
            return ResponseEntity.noContent().build(); // Status 204
        } else {
            return ResponseEntity.notFound().build(); // Status 404
        }
    }
}