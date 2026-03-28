package com.example.frontend.controller;

import com.example.frontend.service.BlockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("entity/soumadwip/Block")
public class BlockController {

    private final BlockService blockService;

    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @GetMapping
    public String blockPage(Model model) {
        model.addAttribute("blocks", blockService.getAllBlocks());
        return "block";
    }

    @PostMapping("/create")
    public String createBlock(@RequestParam int blockFloor,
                              @RequestParam int blockCode) {

        Map<String, Object> block = new LinkedHashMap<>();
        block.put("blockFloor", blockFloor);
        block.put("blockCode", blockCode);

        blockService.createBlock(block);
        return "redirect:/entity/soumadwip/Block";
    }

    @PostMapping("/update")
    public String updateBlock(@RequestParam int blockFloor,
                              @RequestParam int blockCode) {

        Map<String, Object> block = new LinkedHashMap<>();
        block.put("blockFloor", blockFloor);
        block.put("blockCode", blockCode);

        blockService.updateBlock(blockFloor, blockCode, block);
        return "redirect:/entity/soumadwip/Block";
    }

    @PostMapping("/delete")
    public String deleteBlock(@RequestParam int blockFloor,
                              @RequestParam int blockCode) {

        blockService.deleteBlock(blockFloor, blockCode);
        return "redirect:/entity/soumadwip/Block";
    }
    @GetMapping("/searchByFloor")
    public String searchByFloor(@RequestParam int blockFloor, Model model) {
        model.addAttribute("blocks", blockService.getAllBlocks());
        model.addAttribute("blocksByFloor", blockService.getBlocksByFloor(blockFloor));
        return "block";
    }

    @GetMapping("/searchByCode")
    public String searchByCode(@RequestParam int blockCode, Model model) {
        model.addAttribute("blocks", blockService.getAllBlocks());
        model.addAttribute("blocksByCode", blockService.getBlocksByCode(blockCode));
        return "block";
    }
}