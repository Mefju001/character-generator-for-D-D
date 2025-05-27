package com.example.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
