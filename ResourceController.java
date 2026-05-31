package com.hasini.PersonalResourceLinkHub;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final List<Resource> resourceList = new ArrayList<>();
    private int currentId = 1;

    public ResourceController() {
        // Sample default data so the dashboard isn't completely empty on startup
        resourceList.add(new Resource(currentId++, "Spring Initializr", "https://start.spring.io", "Backend"));
        resourceList.add(new Resource(currentId++, "MDN Web Docs", "https://developer.mozilla.org", "Frontend"));
    }

    // GET: Fetch all links
    @GetMapping
    public ResponseEntity<List<Resource>> getAll() {
        return ResponseEntity.ok(resourceList);
    }

    // POST: Save a new link
    @PostMapping
    public ResponseEntity<Resource> create(@RequestBody Resource newResource) {
        newResource.setId(currentId++);
        resourceList.add(newResource);
        return ResponseEntity.status(HttpStatus.CREATED).body(newResource);
    }

    // DELETE: Remove a link using its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean removed = resourceList.removeIf(r -> r.getId() == id);
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}