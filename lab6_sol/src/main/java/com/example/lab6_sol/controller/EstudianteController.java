package com.example.lab6_sol.controller;

import com.example.lab6_sol.entity.Usuario;
import com.example.lab6_sol.entity.Curso;
import com.example.lab6_sol.repository.UsuarioRepository;
import com.example.lab6_sol.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/lista")
    public String listaUsuarios(Model model){
        List<Usuario> estudiantes = usuarioRepository.findByRolid(5);
        model.addAttribute("estudiantes", estudiantes);
        return "lista_usuarios";
    }

    @GetMapping("/new")
    public String nuevoUsuarioFrm(Model model, @ModelAttribute("usuario") Usuario usuario) {
        model.addAttribute("listaCursos", CursoRepository.findAll());
        return "estudiante/newFrm";
    }
    @GetMapping("/edit")
    public String editarUsuario(@ModelAttribute("usuario") Usuario usuario, Model model, @RequestParam("id") int id) {

        Optional<Curso> optCurso = CursoRepository.findById(id);

        if (optCurso.isPresent()) {
            Curso curso = optCurso.get();
            model.addAttribute("listaCursos", curso);
            return "estrudiante/newFrm";
        } else {
            return "redirect:/estudiante";
        }
    }

    @GetMapping("/save")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes attr) {

        if (Usuario.getId() == 0) {
            attr.addFlashAttribute("msg", "Producto creado exitosamente")
        } else {
            attr.addFlashAttribute("msg", "Producto actualizado exitosamente")
        }
    }
}
