package com.Daniel_Muntean4.pomodoro_api;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Pageable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sessions")

@Tag(name = "Sessions",description = "Manage Sessions of Pomodoro for study")
public class SessionController {
    private final SessionsRepository sessionsRepository;
    private final SessionService sessionService;
    public SessionController(SessionsRepository sessionsRepository, SessionService sessionService) {
        this.sessionsRepository = sessionsRepository;
        this.sessionService = sessionService;
    }
    @Operation(summary = "Add session")
    @ApiResponse(responseCode = "201", description = "Session created")
    @ApiResponse(responseCode = "400", description = "Invalid session data", content = @Content )
    @PostMapping("")
    public ResponseEntity<SessionResponse> save(@Valid @RequestBody SessionRequest session) {

        SessionResponse
                saved = sessionService.mapEntityToResponse(sessionsRepository.
                save(sessionService.mapRequestToEntity(session)));
        return ResponseEntity.
                created(URI.create("/api/sessions/"+saved.id())).body(saved);
    }
    @Operation(summary = "Obtain a page of 15 sessions")
    @ApiResponse(responseCode = "203", description = "Session retrieved")
    @ApiResponse(responseCode = "400", description = "Invalid session data", content = @Content )
    @GetMapping("")
    public Page<SessionResponse> getSessions(
            @Parameter(description = "Filter by topic" )@RequestParam(required = false) Long topicId,
            @RequestParam(required = false) Long after,
            @PageableDefault(size = 15) Pageable pageable) {
        return sessionsRepository.search(topicId,after,pageable).map(sessionService::mapEntityToResponse);
    }

    @Operation(summary = "Obtain a session in the id ")
    @GetMapping("/{id}")
    public SessionResponse getSession(@Parameter(description = "id of the session to retrieve") @PathVariable Long id) {
        return sessionService.mapEntityToResponse(sessionsRepository
                .findById(id).orElseThrow(()->
                        new ResponseStatusException
                                (HttpStatus.NOT_FOUND,"Session not found")));
    }
    @Operation(summary = "Update a session in the id ")
    @PutMapping("/{id}")
    public SessionResponse updateSession( @PathVariable Long id, @Valid @RequestBody SessionRequest sessionRequest) {
        Session session = sessionService.mapRequestToEntity(sessionRequest);
        session.setId(id);
        return sessionService.mapEntityToResponse(sessionsRepository.save(session));
    }
    @Operation(summary = "Update a part of a session in the id ")

    @PatchMapping("/{id}")
    public SessionResponse patchSession( @PathVariable Long id, @RequestBody SessionPatchRequest patch) {
    Session existing = sessionsRepository.findById(id).orElseThrow(()->
            new ResponseStatusException
                    (HttpStatus.NOT_FOUND,"Session not found"));
    existing = sessionService.patch(existing,patch);
        return sessionService.mapEntityToResponse(sessionsRepository.save(existing));
    }
    @Operation(summary = "Delete a session located in the id ")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession( @PathVariable Long id) {
         sessionsRepository.deleteById(id);
         return ResponseEntity.noContent().build();
    }
}

