package com.example.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/CharacterGenerator")
public class CharacterController {
    private final CharacterService characterService;
    @Autowired
    public CharacterController(CharacterService characterService){
        this.characterService =  characterService;
    }
    @PostMapping("/generate")
    public ResponseEntity<CharacterJson>generate(@RequestBody Map<String,Object>DTO)
    {
        String race = (String) DTO.get("race");
        String characterClass = (String) DTO.get("characterClass");
        int level = (int) DTO.get("level");
        return ResponseEntity.ok(characterService.generate(race,characterClass,level));
    }
    @GetMapping( "/generate")
    public ResponseEntity<Map<Map<String,String>,List<String>>>generateCharacter()
    {
        return ResponseEntity.ok(characterService.generateCharacter());
    }
}
