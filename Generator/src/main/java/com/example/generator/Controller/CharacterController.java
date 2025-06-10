package com.example.generator.Controller;

import com.example.generator.Data.CharacterJson;
import com.example.generator.Data.CharactersData;
import com.example.generator.Data.EquipmentData;
import com.example.generator.Data.Spell;
import com.example.generator.Service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<CharactersData>generateCharacter()
    {
        return ResponseEntity.ok(characterService.generateCharacter());
    }
    @GetMapping( "/startingEquipment/{characterClass}")
    public ResponseEntity<EquipmentData>generateStartingEquipment(@PathVariable String characterClass)
    {
        return ResponseEntity.ok(characterService.generateStartingEquipment(characterClass));
    }
    @GetMapping( "/class/{characterClass}/level/{level}/spells")
    public ResponseEntity<List<Spell>>generateKnownSpells(@PathVariable String characterClass, @PathVariable int level)
    {
        return ResponseEntity.ok(characterService.generateKnownSpells(characterClass,level));
    }
}
