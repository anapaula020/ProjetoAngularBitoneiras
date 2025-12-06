package br.unitins.tp1.service;

import java.io.File;
import java.io.IOException;

import org.jboss.resteasy.reactive.multipart.FileUpload;

public interface FileService {
    void salvar(Long id, FileUpload file) throws IOException;
    File download(String nomeArquivo); 
}