package mssaat.org.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import mssaat.org.model.Novel;
import mssaat.org.repository.NovelRepository;
import mssaat.org.validation.ValidationException;

@ApplicationScoped
public class NovelFileServiceImpl implements FileService {
    private final String PATH_USER = System.getProperty("user.dir") + File.separator + "images" + File.separator + "novels" + File.separator;

    @Inject
    NovelRepository novelRepository;

    @Override
    @Transactional
    public void salvar(Long id, String imageUrl, byte[] imagem) {
        Novel novel = novelRepository.findById(id);
        if (novel == null){
            throw new ValidationException("id", "Novel não encontrado.");
        }
        String deletar = novel.getImageUrl();

        try {
            novel.setImageUrl(salvarImagem(imageUrl, imagem));
            deleteImagem(deletar);
        } catch (IOException e) {
            throw new ValidationException("imagem", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteImagem(Long id) {
        Novel novel = novelRepository.findById(id);
        if(novel == null) return;
        deleteImagem(novel.getImageUrl());
        novel.setImageUrl(null);
    }

    @Override
    @Transactional
    public void deleteImagem(String imageUrl) {
        File file = new File(PATH_USER + imageUrl);
        if (file.exists()) {
            file.delete();
        }
    }

    public String salvarImagem(String imageUrl, byte[] imagem) throws IOException {
        String mimeType = Files.probeContentType(new File(imageUrl).toPath());
        List<String> listMimeType = Arrays.asList("image/jpg", "image/gif", "image/png", "image/jpeg");
        if (!listMimeType.contains(mimeType)) {
            System.out.println(imageUrl+"tipo não suportado");
            throw new IOException("Tipo de imagem não suportado.");
        }

        if (imagem.length > 1024 * 1024 * 10) {
            throw new IOException("Arquivo muito grande, tamanho máximo 10mb.");
        }

        File diretorio = new File(PATH_USER);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        String nomeArquivo = UUID.randomUUID() + "." + mimeType.substring(mimeType.lastIndexOf("/") + 1);

        File file = new File(PATH_USER + nomeArquivo);
        if (file.exists()) {
            throw new IOException("Este arquivo ja existe.");
        }

        file.createNewFile();

        FileOutputStream stream = new FileOutputStream(file);
        stream.write(imagem);
        stream.flush();
        stream.close();

        return nomeArquivo;
    }

    @Override
    public File download(String imageUrl) {
        if (imageUrl == null) {
            return null;
        }
        return new File(PATH_USER + imageUrl);
    }
}