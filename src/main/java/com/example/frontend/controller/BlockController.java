package com.example.frontend.controller;

import com.example.frontend.service.BlockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("entity/soumadwip/Block")
public class BlockController {

    private final BlockService blockService;

    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    // Main Hub (The card grid)
    @GetMapping("")
    public String blockPage() {
        return "block/block";
    }

    // 1. Get All Blocks
    @GetMapping("/all")
    public String getAllBlocks(Model model) {
        model.addAttribute("blocks", blockService.getAllBlocks());
        return "block/all-blocks";
    }

    // 2. Search by Floor
    @GetMapping("/searchByFloor")
    public String searchByFloor(@RequestParam(required = false) Integer blockFloor, Model model) {
        if (blockFloor != null) {
            model.addAttribute("blocksByFloor", blockService.getBlocksByFloor(blockFloor));
        }
        return "block/block-floor";
    }

    // 3. Search by Code
    @GetMapping("/searchByCode")
    public String searchByCode(@RequestParam(required = false) Integer blockCode, Model model) {
        if (blockCode != null) {
            model.addAttribute("blocksByCode", blockService.getBlocksByCode(blockCode));
        }
        return "block/block-code";
    }

    // 4. Create Form & Action
    @GetMapping("/createForm")
    public String showCreateForm() {
        return "block/block-create";
    }

    @PostMapping("/create")
    public String createBlock(@RequestParam int blockFloor,
                              @RequestParam int blockCode,
                              RedirectAttributes redirectAttributes) {
        try {
            Map<String, Object> block = new LinkedHashMap<>();
            block.put("blockFloor", blockFloor);
            block.put("blockCode", blockCode);

            blockService.createBlock(block);
            redirectAttributes.addFlashAttribute("successMessage", "Block created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Create failed: " + e.getMessage());
        }
        return "redirect:/entity/soumadwip/Block/createForm";
    }
}