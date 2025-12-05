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
import mssaat.org.model.Manga;
import mssaat.org.repository.MangaRepository;
import mssaat.org.validation.ValidationException;

@ApplicationScoped
public class MangaFileServiceImpl implements FileService {
    private final String PATH_USER = System.getProperty("user.dir") + File.separator + "images" + File.separator + "mangas" + File.separator;

    @Inject
    MangaRepository mangaRepository;

    @Override
    @Transactional
    public void salvar(Long id, String imageUrl, byte[] imagem) {
        if (id == null) {
            throw new ValidationException("id", "ID cannot be null");
        }
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new ValidationException("imageUrl", "Image URL cannot be null or empty");
        }
        if (imagem == null) {
            throw new ValidationException("imagem", "Image cannot be null");
        }

        Manga manga = mangaRepository.findById(id);
        if (manga == null) {
            throw new ValidationException("manga", "Manga not found");
        }
        if (manga.getImageUrl() != null) {
            String deletar = manga.getImageUrl();
            deleteImagem(deletar);
        }
        try {
            manga.setImageUrl(salvarImagem(imageUrl, imagem));
        } catch (IOException e) {
            throw new ValidationException("imagem", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteImagem(Long id) {
        Manga manga = mangaRepository.findById(id);
        if (manga == null) return;
        deleteImagem(manga.getImageUrl());
        manga.setImageUrl(null);
    }

    @Override
    @Transactional
    public void deleteImagem(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return;
        }
        // File file = new File(PATH_USER + imageUrl);
        // if (file.exists()) {
        //     file.delete();
        // }
    }

    @Override
    @Transactional
    public String salvarImagem(String imageUrl, byte[] imagem) throws IOException {
        System.out.println("imagem: " + imageUrl);
        if (imagem == null) {
            throw new ValidationException("imagem", "A imagem não pode ser nula");
        }

        // Sanitize the file name
        String sanitizedFileName = sanitizeFileName(imageUrl);

        String mimeType = Files.probeContentType(new File(sanitizedFileName).toPath());
        List<String> listMimeType = Arrays.asList("image/jpg", "image/gif", "image/png", "image/jpeg");
        if (!listMimeType.contains(mimeType)) {
            throw new IOException("Tipo de imagem não suportado.");
        }

        if (imagem.length > 1024 * 1024 * 10) {
            throw new IOException("Arquivo muito grande, tamanho máximo 10MB.");
        }

        File diretorio = new File(PATH_USER);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        String nomeArquivo = UUID.randomUUID() + "." + mimeType.substring(mimeType.lastIndexOf("/") + 1);

        File file = new File(PATH_USER + nomeArquivo);
        if (file.exists()) {
            throw new IOException("Este arquivo já existe.");
        }

        file.createNewFile();

        try (FileOutputStream stream = new FileOutputStream(file)) {
            stream.write(imagem);
            stream.flush();
        }

        return nomeArquivo;
    }

    @Override
    public File download(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return null;
        }
        return new File(PATH_USER + imageUrl);
    }

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }
}