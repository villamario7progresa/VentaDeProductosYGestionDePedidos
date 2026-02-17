package progresa.ventadeproductosygestiondepedidos.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progresa.ventadeproductosygestiondepedidos.Entity.Usuario;
import progresa.ventadeproductosygestiondepedidos.dao.UsuarioRepository;
import progresa.ventadeproductosygestiondepedidos.dto.LoginDTO;
import progresa.ventadeproductosygestiondepedidos.dto.Mensaje;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepo;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        if (StringUtils.isBlank(usuario.getNombre()) || StringUtils.isBlank(usuario.getEmail())) {
            return new ResponseEntity(new Mensaje("Datos incompletos"), HttpStatus.BAD_REQUEST);
        }
        usuarioRepo.save(usuario);
        return new ResponseEntity(new Mensaje("Usuario registrado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {
        return usuarioRepo.findByEmail(login.getEmail())
                .filter(u -> u.getPassword().equals(login.getPassword()))
                .map(u -> ResponseEntity.ok(u))
                .orElse(new ResponseEntity(new Mensaje("Login fallido"), HttpStatus.UNAUTHORIZED));
    }
}
