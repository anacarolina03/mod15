package br.com.ebac.meme.Controller;

import br.com.ebac.meme.Entitie.Meme;
import br.com.ebac.meme.exceptions.ExceptionResponse;
import br.com.ebac.meme.services.MemeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memelandia")
@Slf4j
public class MemeController {

    @Autowired
    private MemeService memeService;


    @GetMapping("/memes")
    public List<Meme> buscaMemes(){
        return memeService.listaTodosMemes();
    }

    @PostMapping("/NovoMeme")
    public Object novoMeme(@RequestBody Meme meme) throws ExceptionResponse {
        return memeService.novoMeme(meme);
    }

    @GetMapping("/memes/id={id}")
    public Meme buscaMemeId(@PathVariable Long id){
        return memeService.buscaMemeId(id);
    }

    @GetMapping("/memes/usuario={usuario}")
    public List<Meme> buscaMemesPorUsuario(@PathVariable String usuario){
        return memeService.buscaMemesPorUsuario(usuario);
    }

    @DeleteMapping("/memes/del/id={id}")
    public void deletaMeme(@PathVariable Long id){
        memeService.deletaMeme(id);
    }

    @GetMapping("/memes/categoria={categoria}")
    public List<Meme> MemePorCategoria(@PathVariable String categoria){
        return memeService.MemePorCategoria(categoria);
    }
}