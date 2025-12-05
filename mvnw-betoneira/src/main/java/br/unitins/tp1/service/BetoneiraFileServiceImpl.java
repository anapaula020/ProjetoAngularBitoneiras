package br.unitins.tp1.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import br.unitins.tp1.model.Betoneira;
import br.unitins.tp1.repository.BetoneiraRepository;
import br.unitins.tp1.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BetoneiraFileServiceImpl implements FileService {
    private final String PATH_USER = System.getProperty("user.dir") + File.separator + "images" + File.separator
            + "betoneiras" + File.separator;

    @Inject
    BetoneiraRepository betoneiraRepository;

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

        Betoneira betoneira = betoneiraRepository.findById(id);
        if (betoneira == null) {
            throw new ValidationException("betoneira", "betoneira not found");
        }
        if (betoneira.getImageUrl() != null) {
            String deletar = betoneira.getImageUrl();
            deleteImagem(deletar);
        }
        try {
            betoneira.setImageUrl(salvarImagem(imageUrl, imagem));
        } catch (IOException e) {
            throw new ValidationException("imagem", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteImagem(Long id) {
        Betoneira betoneira = betoneiraRepository.findById(id);
        if (betoneira == null)
            return;
        deleteImagem(betoneira.getImageUrl());
        betoneira.setImageUrl(null);
    }

    @Override
    @Transactional
    public void deleteImagem(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return;
        }
        File file = new File(PATH_USER + imageUrl);
        if (file.exists()) {
            file.delete();
        }
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