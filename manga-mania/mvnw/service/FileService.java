package mssaat.org.service;

import java.io.File;
import java.io.IOException;

public interface FileService {
    public void salvar(Long id, String imageUrl, byte[] imagem);

    public String salvarImagem(String imageUrl, byte[] imagem) throws IOException;

    public void deleteImagem(Long id) throws IOException;

    public void deleteImagem(String imageUrl) throws IOException;

    public File download(String imageUrl);
}