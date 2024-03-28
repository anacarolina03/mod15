package br.com.ebac.meme.services;

import br.com.ebac.meme.ApiServices.ApiServiceCategoria;
import br.com.ebac.meme.ApiServices.ApiServiceUsuario;
import br.com.ebac.meme.Repository.MemeRepository;
import br.com.ebac.meme.Entitie.Meme;
import br.com.ebac.meme.exceptions.ApiResponseException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class MemeService {

    Logger logger = LogManager.getLogger(MemeService.class);

    @Autowired
    private MemeRepository memeRepository;

    @Autowired
    private ApiServiceUsuario apiServiceUsuario;

    @Autowired
    private ApiServiceCategoria apiServiceCategoria;

    private Meme meme;


    public List<Meme> listaTodosMemes(){
        logger.info("\u001B[34mBuscando lista de memes: " + memeRepository.count());
        return memeRepository.findAll();
    }

    public Meme buscaMemeId(Long id){
        logger.info("\u001B[34mBuscando meme id: " + id);
        return memeRepository.findById(id).get();
    }

    public Object  novoMeme(Meme meme) throws ApiResponseException {
        StringBuilder errolog = new StringBuilder();

        boolean isCategoriaValid = isCategoriaInApiResponse(meme);
        boolean isUsuarioNameValid = isUsuarioNameInApiResponse(meme);

        if (!isCategoriaValid) {
            logger.error("Categoria não encontrada: " + meme.getCategoria());
            errolog.append("Categoria não encontrada: ").append(meme.getCategoria());
        }

        if (!isUsuarioNameValid) {
            if (errolog.length() > 0) {
                errolog.append(" " +
                        ";" + " ");
            }
            logger.error("Usuário não encontrado: " + meme.getUsuario());
            errolog.append("Usuário não encontrado: ").append(meme.getUsuario());
        }

        if (errolog.length() > 0) {
            throw new ApiResponseException(errolog.toString());
        }

        if (isCategoriaValid && isUsuarioNameValid) {
            logger.info("\u001B[34mMeme inserido com sucesso! :" + meme.getNome());
            return memeRepository.save(meme);
        }

        return errolog.toString();
    }

    public boolean isUsuarioNameInApiResponse(Meme meme) {
        String targetUsuarioName = meme.getUsuario();
        String apiResponses = apiServiceUsuario.fetchDataFromApi();

        JsonArray usuariosArray = new Gson().fromJson(apiResponses, JsonArray.class);

        for (JsonElement usuarioElement : usuariosArray) {
            JsonObject usuarioObject = usuarioElement.getAsJsonObject();
            String nome = usuarioObject.get("nome").getAsString();

            if (nome.equals(targetUsuarioName)) {
                System.out.println("Usuario encontrado");
                return true;
            }
        }

        System.out.println("Usuario não encontrado");
        return false;
    }


    public boolean isCategoriaInApiResponse(Meme meme) {
        String tergetCategoriaName = meme.getCategoria();
        String apiResponses = apiServiceCategoria.fetchDataFromApi();

        JsonArray categoriaArray = new Gson().fromJson(apiResponses, JsonArray.class);

        for (JsonElement categoriaElement : categoriaArray) {
            JsonObject categoriaObject = categoriaElement.getAsJsonObject();
            String categoria = categoriaObject.get("nome").getAsString();

            if (categoria.equals(tergetCategoriaName)) {
                return true;
            }
        }

        return false;
    }



    public List<Meme> buscaMemesPorUsuario(String usuario){
        try {
            logger.info("\u001B[34mBuscando memes por usuario: " + usuario + "\u001B[0m");
            return memeRepository.buscaMemesPorUsuario(usuario);
        } catch (ApiResponseException e) {
            logger.error("Usuario não encontrado " + usuario);
            throw new ApiResponseException("Usuario não encontrado " + usuario);
        }
    }

    public void deletaMeme(Long id){
        logger.info("\u001B[34mDeletando meme: " + id);
        memeRepository.deleteById(id);
    }

    public List<Meme> MemePorCategoria(String categoria){
        try {
            logger.info("\u001B[34mBuscando memes por categoria: " + categoria + "\u001B[0m");
            return memeRepository.MemePorCategoria(categoria);
        } catch (ApiResponseException e) {
            logger.error("Categoria não encontrada" + categoria);
            throw new ApiResponseException("Categoria não encontrada" + categoria);
        }
    }

}