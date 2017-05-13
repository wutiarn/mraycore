package ru.mray.core.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/eurovision")
class EuroController {
    @RequestMapping
    fun donatePage(): String {
        return "euro"
    }
}